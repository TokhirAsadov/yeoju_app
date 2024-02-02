package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.table;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.io.IOException;

public interface TableService {
    ApiResponse saveFileToSystem(User user, MultipartHttpServletRequest request, Integer year, String month,String kafedraId, String id);
    ApiResponse findByKafedraId(String kafedraId);

    ApiResponse findByName(String name);


    byte[] downloadImageFromFileSystem(String fileName) throws IOException;

}
