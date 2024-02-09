package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeForAttendance;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.GradeForAttendanceDto;

public interface GradeForAttendanceService {
    ApiResponse createGradeForAttendance(User user,GradeForAttendanceDto dto);
}
