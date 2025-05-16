package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.topicFileOfLine;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.entity.moduleV2.LessonModule;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.moduleV2.LessonModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TopicFileOfLineV2Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TopicFileOfLineV2ImplService implements TopicFileOfLineV2Service {
    private final TopicFileOfLineV2Repository fileRepository;
    private final LessonModuleRepository lessonModuleRepository;

    @Override
    public byte[] downloadImageFromFileSystem(String subject,String fileName) throws IOException {
        byte[] images = Files.readAllBytes(new File("subjects\\"+subject+"\\"+fileName).toPath());
        return images;
    }

    @Override
    public ApiResponse findByName(String name) {
        Optional<TopicFileOfLineV2> optional = fileRepository.findTopicFileOfLineV2ByName(name);
        return optional.map(topicFileOfLine -> new ApiResponse(true, "topic", topicFileOfLine)).orElseGet(() -> new ApiResponse(false, "not found file by " + name));
    }


    @SneakyThrows
    @Override
    public ApiResponse saveFileToSystem(
            MultipartHttpServletRequest request,
            String lessonModuleId,
            TopicFileType type,
            String fileName,
            String fileUrl
    ) {
        Path folder = Paths.get("subjects");

        if (Files.exists(folder)) {
            // file exist
            System.out.println("File exists");
        }
        if (Files.notExists(folder)) {
            // file is not exist
            System.out.println("file is not exist");
            File f = new File("subjects");
            f.mkdir();
        }

        Optional<LessonModule> lessonModuleOptional = lessonModuleRepository.findById(lessonModuleId);
        if (lessonModuleOptional.isPresent()) {
            LessonModule lessonModule = lessonModuleOptional.get();
            Path path = Paths.get("subjects\\" + lessonModule.getModule().getCourse().getPlan().getSubject().getName());

            if(Files.notExists(path)) {
                File f = new File("subjects\\" + lessonModule.getModule().getCourse().getPlan().getSubject().getName());
                f.mkdirs();
            }

            if (type==TopicFileType.FILE) {

                Iterator<String> fileNames = request.getFileNames();
                while (fileNames.hasNext()) {
                    MultipartFile file = request.getFile(fileNames.next());
                    if (file != null) {
                        System.out.println(file.getOriginalFilename());
                        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                        String name = fileName + "_" + UUID.randomUUID();
                        Path filePath = Paths.get("subjects\\" + lessonModule.getModule().getCourse().getPlan().getSubject().getName() + "\\" + name + substring);
                        Files.copy(file.getInputStream(), filePath);

                        TopicFileOfLineV2 topicFileOfLine = new TopicFileOfLineV2();
                        topicFileOfLine.setFileType(substring);
                        topicFileOfLine.setType(type);
                        topicFileOfLine.setName(name);
                        topicFileOfLine.setContentType(file.getContentType());

                        Set<LessonModule> lessonModules = new HashSet<>();
                        lessonModules.add(lessonModule);
                        topicFileOfLine.setLessons(lessonModules);

                        fileRepository.save(topicFileOfLine);
                    }
                }
            }
            else {
                String name = fileName + "_" + UUID.randomUUID();
                TopicFileOfLineV2 topicFileOfLine = new TopicFileOfLineV2();
                topicFileOfLine.setType(type);
                topicFileOfLine.setName(name);

                Set<LessonModule> lessonModules = new HashSet<>();
                lessonModules.add(lessonModule);
                topicFileOfLine.setLessons(lessonModules);

                fileRepository.save(topicFileOfLine);
            }

            return new ApiResponse(true,"save file successfully");
        }

        throw new UserNotFoundException("not fount module by id: "+lessonModuleId);
    }
}
