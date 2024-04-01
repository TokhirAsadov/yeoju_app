package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.ForUserSaveDto;

public interface AdminService {
    ApiResponse saveOrUpdateUser(ForUserSaveDto dto);
    ApiResponse getUserForUpdate(String param);
    ApiResponse getUserForUpdateById(String id);
    ApiResponse getInformationAboutCountOfUsers();
}
