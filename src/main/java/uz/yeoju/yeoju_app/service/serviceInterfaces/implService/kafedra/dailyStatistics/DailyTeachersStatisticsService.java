package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface DailyTeachersStatisticsService {
    void scheduleForSaveDailyTeachersStatistics();
    void scheduleForSaveDailyTeachersStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday);

    ApiResponse getDailyTeacherStatisticsByDay(String teacherId, Integer year, Integer month, Integer day);
}
