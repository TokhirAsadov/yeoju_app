package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface UserDiplomaImplService<T> extends MainService<T> {
    ApiResponse findUserDiplomasByUserId(String user_id);
    ApiResponse findUserDiplomasByActive(boolean active);
}
