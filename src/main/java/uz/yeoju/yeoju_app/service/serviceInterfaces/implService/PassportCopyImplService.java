package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface PassportCopyImplService<T> extends MainService<T> {
    ApiResponse findPassportCopiesByUserId(String user_id);
    ApiResponse findPassportCopiesByActive(boolean active);
}
