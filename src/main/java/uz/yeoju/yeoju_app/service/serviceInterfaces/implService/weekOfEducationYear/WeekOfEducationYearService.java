package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.weekOfEducationYear;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.WeekOfYearDto;

public interface WeekOfEducationYearService {

    ApiResponse findAll();
    ApiResponse findById(String id);
    ApiResponse saveOrUpdate(WeekOfYearDto dto);
    ApiResponse saveV2(WeekOfYearDto dto);

    ApiResponse deletedById(String id);

}
