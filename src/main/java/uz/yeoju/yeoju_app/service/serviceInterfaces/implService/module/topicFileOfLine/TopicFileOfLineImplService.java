package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.topicFileOfLine;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.module.LineOfPlan;
import uz.yeoju.yeoju_app.entity.module.TopicFileOfLine;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.module.LineOfPlanRepository;
import uz.yeoju.yeoju_app.repository.module.TopicFileOfLineRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TopicFileOfLineImplService implements TopicFileOfLineService{
    private final TopicFileOfLineRepository fileRepository;
    private final LineOfPlanRepository lineRepository;

    @Override
    public byte[] downloadImageFromFileSystem(String subject,String fileName) throws IOException {
        byte[] images = Files.readAllBytes(new File("subjects\\"+subject+"\\"+fileName).toPath());
        return images;
    }

    @Override
    public ApiResponse findByName(String name) {
        Optional<TopicFileOfLine> optional = fileRepository.findTopicFileOfLineByName(name);
        return optional.map(topicFileOfLine -> new ApiResponse(true, "topic", topicFileOfLine)).orElseGet(() -> new ApiResponse(false, "not found file by " + name));
    }


    @SneakyThrows
    @Override
    public ApiResponse saveFileToSystem(
            MultipartHttpServletRequest request,
            String lineId,
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

        Optional<LineOfPlan> lineOptional = lineRepository.findById(lineId);
        if (lineOptional.isPresent()) {
            LineOfPlan line = lineOptional.get();
            Path path = Paths.get("subjects\\" + line.getPlanOfSubject().getSubject().getName());

            if(Files.notExists(path)) {
                File f = new File("subjects\\" + line.getPlanOfSubject().getSubject().getName());
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
                        Path filePath = Paths.get("subjects\\" + line.getPlanOfSubject().getSubject().getName() + "\\" + name + substring);
                        Files.copy(file.getInputStream(), filePath);


                        TopicFileOfLine topicFileOfLine = new TopicFileOfLine();
                        topicFileOfLine.setFileType(substring);
                        topicFileOfLine.setType(type);
                        topicFileOfLine.setName(name);
                        topicFileOfLine.setContentType(file.getContentType());

                        Set<LineOfPlan> lines = new HashSet<>();
                        lines.add(line);
                        topicFileOfLine.setLineOfPlans(lines);

                        fileRepository.save(topicFileOfLine);
                    }
                }
            }
            else {
                String name = fileName + "_" + UUID.randomUUID();
                TopicFileOfLine topicFileOfLine = new TopicFileOfLine();
                topicFileOfLine.setType(type);
                topicFileOfLine.setName(name);

                Set<LineOfPlan> lines = new HashSet<>();
                lines.add(line);
                topicFileOfLine.setLineOfPlans(lines);
                topicFileOfLine.setUrl(fileUrl);

                fileRepository.save(topicFileOfLine);
            }

            return new ApiResponse(true,"save file successfully");
        }



        return new ApiResponse(false,"not fount line by id: "+lineId);
    }
}
