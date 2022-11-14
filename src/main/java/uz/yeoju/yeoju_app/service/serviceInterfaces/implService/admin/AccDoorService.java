package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.AccDoorDto;

public interface AccDoorService {

    ApiResponse findAll();
    ApiResponse findById(Long id);
    ApiResponse save(AccDoorDto dto);

    ApiResponse findByDeviceId(Long deviceId);

    ApiResponse getPortsByDeviceId(Long deviceId);

    ApiResponse getDevicesList();

}
