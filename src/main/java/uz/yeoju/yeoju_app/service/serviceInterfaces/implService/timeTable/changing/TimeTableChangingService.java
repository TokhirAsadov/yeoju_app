package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing;

import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingRoomOfLessonDetailsDto;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherDetailsDto;

public interface TimeTableChangingService {
    ApiResponse changingTeacherData(ChangingTeacherDetailsDto dto);
    ApiResponse changingRoomOfLesson(ChangingRoomOfLessonDetailsDto dto);
    ApiResponse getDataOfTeachers(WeekType weekType, Integer year, Integer week);
}
