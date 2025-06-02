package uz.yeoju.yeoju_app.controller.moduleV2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.topicFileOfLine.TopicFileOfLineV2Service;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/topicFileV2")
@RequiredArgsConstructor
public class TopicFileOfLineV2Controller {
    private final TopicFileOfLineV2Service service;


    @GetMapping("/uploadFromSystem")
    public HttpEntity<?> download(
            @CurrentUser User user,
            @RequestParam("fileName") String fileName
            ) throws IOException {

        ApiResponse topic = service.findByName(fileName);
        if (topic.isSuccess()) {
            TopicFileOfLineV2 topicObj = (TopicFileOfLineV2) topic.getObj();

            byte[] imageData=service.downloadImageFromFileSystem(topicObj.getPackageName(),fileName+topicObj.getFileType());
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
            @RequestParam("fileName") String fileName
    ) throws IOException {

        ApiResponse topic = service.findByName(fileName);
        if (topic.isSuccess()) {
            TopicFileOfLineV2 topicObj = (TopicFileOfLineV2) topic.getObj();

            byte[] imageData=service.downloadImageFromFileSystem(topicObj.getPackageName(),fileName+topicObj.getFileType());
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
    @PostMapping("/upload/{moduleId}/{fileName}")
    public HttpEntity<?> uploadXml(
            MultipartHttpServletRequest request,
            @PathVariable("moduleId") String lessonModuleId,
            @PathVariable("fileName") String fileName,
            @RequestParam("type") TopicFileType type,
            @RequestParam("fileUrl") String fileUrl
    ) {

        ApiResponse apiResponse = service.saveFileToSystem(request,lessonModuleId,type,fileName,fileUrl);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete")
    public HttpEntity<?> deleteFile(
            @RequestParam("fileName") String fileName
    ) {
        ApiResponse apiResponse = service.deleteFileFromSystem(fileName);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 404).body(apiResponse);
    }

}
