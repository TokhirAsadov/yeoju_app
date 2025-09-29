package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import org.springframework.http.ResponseEntity;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraStatistics;

import java.util.List;

public interface KafedraTeachersStatisticsService {
    void scheduleForSaveDailyStatistics();
    void scheduleForSaveDailyStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday);
    ApiResponse getAllKafedrasTeachersStatistics(int year, int week);
    ApiResponse getKafedrasTeachersStatistics(int year, int week, String kafedraId);
    ApiResponse getKafedrasTeachersStatisticsByDay(String day);
    ResponseEntity<List<GetKafedraStatistics>> getByWeekRange(String start, String end);
    ResponseEntity<GetKafedraStatistics> getByWeekRangeByKafedraId(String kafedraId, String start, String end);
}
