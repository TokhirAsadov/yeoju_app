package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface PhoneNumberImplService<T> extends MainService<T> {
    ApiResponse findPhoneNumbersByUser(UserDto userDto);
}
