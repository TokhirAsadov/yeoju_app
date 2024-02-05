package uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.teacher.TeachersFreeHours;
import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface TeachersFreeHoursService {

    ApiResponse getAllHoursByTeacherId(String teacherId);
    ApiResponse createNewHour(User user, TeachersFreeHours hour);
}
