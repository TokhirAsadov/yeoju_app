package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface UserImplService<T> extends MainService<T> {
    T getUserByLogin(String login);
    T getUserByRFID(String rfid);
    ApiResponse getUserByEmail(String email);
    boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID);
}
