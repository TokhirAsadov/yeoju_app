package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.educationYear;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearDto;

public interface EducationYearService {

    ApiResponse findAll();
    ApiResponse findById(String id);

    ApiResponse getGroupsByFacultyId(String id);
    ApiResponse saveOrUpdate(EducationYearDto dto);


    ApiResponse deletedById(String id);

    ApiResponse educationYearsForSelected();
    ApiResponse educationYearsForCRUD();
    ApiResponse getSortNumberOfWeek(String educationYearId);
}
