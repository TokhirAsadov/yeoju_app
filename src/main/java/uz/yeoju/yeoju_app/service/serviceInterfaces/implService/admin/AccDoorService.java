package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.AccDoorDto;

import java.util.List;

public interface AccDoorService {

    ApiResponse findAll();
    ApiResponse findById(Long id);
    ApiResponse save(AccDoorDto dto);

    ApiResponse deleteById(Long id);

    ApiResponse findByDeviceId(Long deviceId);

    ApiResponse getPortsByDeviceId(Long deviceId);

    ApiResponse getDevicesList();

}
