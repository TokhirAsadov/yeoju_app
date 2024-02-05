package uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface TeachersFreeHoursService {

    ApiResponse getAllHoursByTeacherId(String teacherId);
}
