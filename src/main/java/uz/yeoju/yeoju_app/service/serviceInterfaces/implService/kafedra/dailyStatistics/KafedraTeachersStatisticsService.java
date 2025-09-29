package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import org.springframework.http.ResponseEntity;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraStatistics;

import java.time.LocalDate;
import java.util.List;

public interface KafedraTeachersStatisticsService {
    void scheduleForSaveDailyStatistics();
    void scheduleForSaveDailyStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday);
    LocalDate[] getWeekRange(int year, int week);
    ApiResponse getAllKafedrasTeachersStatistics(int year, int week);

}
