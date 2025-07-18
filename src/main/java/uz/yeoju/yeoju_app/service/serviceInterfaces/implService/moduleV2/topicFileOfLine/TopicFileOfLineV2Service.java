package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.topicFileOfLine;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.io.IOException;

public interface TopicFileOfLineV2Service {
    ApiResponse saveFileToSystem(MultipartHttpServletRequest request, String moduleId,TopicFileType type, String fileName, String fileUrl);
    byte[] downloadImageFromFileSystem(String packageName,String fileName) throws IOException;
    ApiResponse findByName(String name);
    ApiResponse deleteFileFromSystem(String fileName);
}
