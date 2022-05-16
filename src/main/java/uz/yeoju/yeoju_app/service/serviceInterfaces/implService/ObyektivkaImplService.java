package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface ObyektivkaImplService<T> extends MainService<T> {
    ApiResponse findObyektivkasByUserId(String user_id);
    ApiResponse findObyektivkasByActive(boolean active);
}
