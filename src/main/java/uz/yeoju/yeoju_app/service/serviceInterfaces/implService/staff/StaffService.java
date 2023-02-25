package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.staff;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.staff.StaffSaveFromSection;
import uz.yeoju.yeoju_app.payload.superAdmin.StaffSaveDto;

import java.util.Date;

public interface StaffService {
    ApiResponse findAll();
    ApiResponse saveStaff(StaffSaveDto dto);
    ApiResponse saveStaff(StaffSaveFromSection dto,String dekanId);

    ApiResponse getStatisticsForRektor(String sectionId, Date date);

    ApiResponse getStaffIdWithUserId(String userId);

    ApiResponse deleteStaff(String staffId,String userId);

    ApiResponse getDekanStatisticsForRektor(String id, Date date);
}
