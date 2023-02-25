package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.address.AddressDto;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.address.SelectAddressDto;

public interface AddressService {

    ApiResponse findAll();
    ApiResponse findById(String id);
    ApiResponse save(AddressDto dto);
    ApiResponse saveForUser(User user,AddressDto dto);

    ApiResponse saveSecond(User user, SelectAddressDto dto);
}
