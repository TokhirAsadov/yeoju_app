package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

public interface DailyTeachersStatisticsService {
    void scheduleForSaveDailyTeachersStatistics();
    void scheduleForSaveDailyTeachersStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday);
}
