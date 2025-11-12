package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.util.Date;
import java.util.Set;

public interface DailyTeachersStatisticsService {
    void scheduleForSaveDailyTeachersStatistics();
    void scheduleForSaveDailyTeachersStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday);

    ApiResponse getDailyTeacherStatisticsByDay(String teacherId, Integer year, Integer month, Integer day);

    ApiResponse getDailyStatisticsByWeek(String teacherId, Integer year, Integer week);

    ApiResponse getDailyStatisticsByMonth(String teacherId, Integer year, Integer month);

    ApiResponse getStatisticsForTable(String kafedraId, Integer year, Integer month, Set<String> teachersIds);

    void qaytaSaveQilish(Integer year, Integer month, Integer day, Integer week, Integer weekday);
}
