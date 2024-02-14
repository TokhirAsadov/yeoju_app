package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dynamicAttendance;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.DynamicAttendanceDto;
import uz.yeoju.yeoju_app.payload.MultiDynamicAttendanceDto;

public interface DynamicAttendanceService {
    ApiResponse createDynamicAttendance(User user, DynamicAttendanceDto dto);
    ApiResponse createMultiDynamicAttendance(User user, MultiDynamicAttendanceDto dto);
}
