package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.topicFileOfLine;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.io.IOException;

public interface TopicFileOfLineService {
    ApiResponse saveFileToSystem(MultipartHttpServletRequest request, String lineId,TopicFileType type, String fileName, String fileUrl);
    byte[] downloadImageFromFileSystem(String subject,String fileName) throws IOException;
    ApiResponse findByName(String name);
}
