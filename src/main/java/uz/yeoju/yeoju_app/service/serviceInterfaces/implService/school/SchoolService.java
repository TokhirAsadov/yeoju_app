package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.school;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.school.SchoolDto;

public interface SchoolService {
    ApiResponse findAll();
    ApiResponse saveOrUpdate(SchoolDto dto);
    ApiResponse findById(String id);
    ApiResponse deleteById(String id);
}
