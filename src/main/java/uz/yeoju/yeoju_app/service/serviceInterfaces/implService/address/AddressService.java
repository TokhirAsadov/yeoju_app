package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.AddressDto;
import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface AddressService {

    ApiResponse findAll();
    ApiResponse findById(String id);
    ApiResponse save(AddressDto dto);
    ApiResponse saveForUser(User user,AddressDto dto);
}
