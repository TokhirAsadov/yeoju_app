package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.module.DownloadCounter;
import uz.yeoju.yeoju_app.entity.module.TopicFileOfLine;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.module.DownloadCounterRepository;
import uz.yeoju.yeoju_app.repository.module.TopicFileOfLineRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.topicFileOfLine.TopicFileOfLineService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/topicFile")
@RequiredArgsConstructor
public class TopicFileOfLineController {
    private final TopicFileOfLineService service;
    private final DownloadCounterRepository downloadRepository;


    @GetMapping("/uploadFromSystem")
    public HttpEntity<?> download(
            @CurrentUser User user,
            @RequestParam("fileName") String fileName,
            @RequestParam("subject") String subject
            ) throws IOException {

        ApiResponse topic = service.findByName(fileName);
        if (topic.isSuccess()) {
            TopicFileOfLine topicObj = (TopicFileOfLine) topic.getObj();

            System.out.println(user.toString());
            System.out.println(user.getId());
            System.out.println(topicObj.toString());
            System.out.println("----------------------------------"+topicObj.getId()+"------------------------------------------------------------");

            // for statistics who downloaded? how many?
            Boolean exists = downloadRepository.existsDownloadCounterByUserIdAndTopicId(user.getId(), topicObj.getId());

            if (exists!=null && exists) {
                Optional<DownloadCounter> downloadCounterOptional = downloadRepository.findDownloadCounterByUserIdAndTopicId(user.getId(), topicObj.getId());
                if (downloadCounterOptional.isPresent()) {
                    DownloadCounter downloadCounter = downloadCounterOptional.get();
                    downloadCounter.setCount(downloadCounter.getCount() + 1);
                    downloadRepository.save(downloadCounter);
                } else {
                    DownloadCounter downloadCounter = new DownloadCounter();
                    downloadCounter.setCount(1);
                    downloadCounter.setTopic(topicObj);
                    downloadCounter.setUser(user);
                    downloadRepository.save(downloadCounter);
                }
            }
            else {
                DownloadCounter downloadCounter = new DownloadCounter();
                downloadCounter.setCount(1);
                downloadCounter.setTopic(topicObj);
                downloadCounter.setUser(user);
                downloadRepository.save(downloadCounter);
            }

            byte[] imageData=service.downloadImageFromFileSystem(subject,fileName+topicObj.getFileType());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(topicObj.getContentType()))
                    .body(imageData);
        }
        else {
            return ResponseEntity.status(403).body(topic);
        }


    }

    @GetMapping("/uploadFromSystemUser")
    public HttpEntity<?> download(
            @RequestParam("fileName") String fileName,
            @RequestParam("subject") String subject
    ) throws IOException {

        ApiResponse topic = service.findByName(fileName);
        if (topic.isSuccess()) {
            TopicFileOfLine topicObj = (TopicFileOfLine) topic.getObj();

            byte[] imageData=service.downloadImageFromFileSystem(subject,fileName+topicObj.getFileType());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(topicObj.getContentType()))
                    .body(imageData);
        }
        else {
            return ResponseEntity.status(403).body(topic);
        }


    }


//    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
//        byte[] imageData=service.downloadImageFromFileSystem(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//
//    }


    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/upload/{lineId}/{fileName}")
    public HttpEntity<?> uploadXml(
            MultipartHttpServletRequest request,
            @PathVariable("lineId") String lineId,
            @PathVariable("fileName") String fileName,
            @RequestParam("type") TopicFileType type,
            @RequestParam("fileUrl") String fileUrl
    ) {

        ApiResponse apiResponse = service.saveFileToSystem(request,lineId,type,fileName,fileUrl);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
