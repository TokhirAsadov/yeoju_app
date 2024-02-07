package uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.teacher.TeachersFreeHoursDto;

public interface TeachersFreeHoursService {

    ApiResponse getAllHoursByTeacherId(String teacherId);
    ApiResponse checkerThatExistsTeachersFreeHours(String educationYearId,String teacherId);
    ApiResponse createNewHour(User user, TeachersFreeHoursDto dto);
    ApiResponse deleteFreeHours(User user, String id);

    ApiResponse allFreeHoursForGroup(String educationYearId, String groupId);
}
