package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dynamicAttendance;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.*;

import java.util.Set;

public interface DynamicAttendanceService {
    ApiResponse createDynamicAttendance(User user, DynamicAttendanceDto dto);
    ApiResponse createMultiDynamicAttendance(User user, MultiDynamicAttendanceDto dto);

    ApiResponse createMultiDynamicAttendance2(User user, MultiDynamicAttendance2Dto dto);

    ApiResponse createMultiDynamicAttendance3(User user, Set<MultiDynamicAttendance3Dto> dtos);
    ApiResponse updateMultiDynamicAttendance3(User user, Set<MultiDynamicAttendance3Dto> dtos);
    ApiResponse updateMultiDynamicAttendance(User user, Set<UpdateMultiDynamicAttendanceDto> dtos);
}
