package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.topicFileOfLine;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
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
    private final ModuleRepository moduleRepository;

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
            String moduleId,
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

        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();
            Path path = Paths.get("subjects\\" + module.getCourse().getPlan().getSubject().getName());

            if(Files.notExists(path)) {
                File f = new File("subjects\\" + module.getCourse().getPlan().getSubject().getName());
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
                        Path filePath = Paths.get("subjects\\" + module.getCourse().getPlan().getSubject().getName() + "\\" + name + substring);
                        Files.copy(file.getInputStream(), filePath);

                        TopicFileOfLineV2 topicFileOfLine = new TopicFileOfLineV2();
                        topicFileOfLine.setFileType(substring);
                        topicFileOfLine.setType(type);
                        topicFileOfLine.setName(name);
                        topicFileOfLine.setContentType(file.getContentType());

                        Set<Module> modules = new HashSet<>();
                        modules.add(module);
                        topicFileOfLine.setModules(modules);

                        fileRepository.save(topicFileOfLine);
                    }
                }
            }
            else {
                String name = fileName + "_" + UUID.randomUUID();
                TopicFileOfLineV2 topicFileOfLine = new TopicFileOfLineV2();
                topicFileOfLine.setType(type);
                topicFileOfLine.setName(name);

                Set<Module> modules = new HashSet<>();
                modules.add(module);
                topicFileOfLine.setModules(modules);

                fileRepository.save(topicFileOfLine);
            }

            return new ApiResponse(true,"save file successfully");
        }

        throw new UserNotFoundException("not fount module by id: "+moduleId);
    }

    @Override
    public ApiResponse deleteFileFromSystem(String fileName, String subjectName) {
        // Fayl katalogi yo'lini tuzish
        String directoryPath = "subjects\\" + subjectName;
        Optional<TopicFileOfLineV2> optional = fileRepository.findTopicFileOfLineV2ByName(fileName);

        if (optional.isPresent()) {
            TopicFileOfLineV2 file = optional.get();

            // Faqat FILE turidagi fayllarni fayl tizimidan o'chirishga harakat qilamiz
            if (file.getType() == TopicFileType.FILE) {
                String fullFileName = file.getName() + file.getFileType(); // UUID_....pdf
                Path filePath = Paths.get(directoryPath, fullFileName);

                try {
                    Files.deleteIfExists(filePath);
                    fileRepository.delete(file);
                    return new ApiResponse(true, "Fayl va ma'lumotlar bazasidan muvaffaqiyatli o'chirildi.");
                } catch (IOException e) {
                    return new ApiResponse(false, "Faylni o'chirishda xatolik: " + e.getMessage());
                }
            } else {
                // FILE bo'lmasa faqat bazadan o'chiriladi
                fileRepository.delete(file);
                return new ApiResponse(true, "Tizimdan hech narsa o'chirilmadi, lekin ma'lumotlar bazasidan o'chirildi.");
            }
        }

        return new ApiResponse(false, "Fayl topilmadi: " + fileName);
    }

}
