package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface UserPhotoImplService<T> extends MainService<T> {
    ApiResponse findUserPhotosByUserId(String user_id);
    ApiResponse findUserPhotosByActive(boolean active);
}
