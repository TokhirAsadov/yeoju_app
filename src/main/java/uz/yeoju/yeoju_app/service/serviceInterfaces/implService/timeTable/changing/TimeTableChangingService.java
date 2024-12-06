package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing;

import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingRoomOfLessonDetailsDto;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherDetailsDto;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherOfLessonDetailsDto;

public interface TimeTableChangingService {
    ApiResponse changingTeacherData(ChangingTeacherDetailsDto dto);
    ApiResponse changingRoomOfLesson(ChangingRoomOfLessonDetailsDto dto);
    ApiResponse changingTeacherOfLesson(ChangingTeacherOfLessonDetailsDto dto);
    ApiResponse getDataOfTeachers(WeekType weekType, Integer year, Integer week);

    ApiResponse getDataOfRooms(WeekType weekType, Integer year, Integer week);
    ApiResponse getDataOfFreeRooms(Integer year, Integer week, String dayCode, Integer period);

    ApiResponse getDataOfFreeTeachers(String kafedraId, Integer year, Integer week, String dayCode, Integer period);
}
