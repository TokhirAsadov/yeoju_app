package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.permissionForTeacherGrading;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreatePermissionForTeacherGradingDto;

import java.util.Set;

public interface PermissionForTeacherGradingService {
    ApiResponse findAllPermissionsForTeacherGrading();
    ApiResponse findAllPermissionsForTeacherGradingByEducationYearIdAndStatus(String educationYearId, PPostStatus status);
    ApiResponse findById(String id);


}
