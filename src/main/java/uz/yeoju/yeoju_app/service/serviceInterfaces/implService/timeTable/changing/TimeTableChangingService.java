package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherDetailsDto;

public interface TimeTableChangingService {
    ApiResponse changingTeacherData(ChangingTeacherDetailsDto dto);
}
