package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticsScheduler {
    private final KafedraTeachersStatisticsService kafedraTeachersStatisticsService;
    private final DailyTeachersStatisticsService dailyTeachersStatisticsService;

    public StatisticsScheduler(KafedraTeachersStatisticsService kafedraTeachersStatisticsService, DailyTeachersStatisticsService dailyTeachersStatisticsService) {
        this.kafedraTeachersStatisticsService = kafedraTeachersStatisticsService;
        this.dailyTeachersStatisticsService = dailyTeachersStatisticsService;
    }

    @Scheduled(cron = "0 0 22 * * ?" , zone = "Asia/Tashkent")
    public void runDailyStatisticsJob() {
        kafedraTeachersStatisticsService.scheduleForSaveDailyStatistics();
    }
    @Scheduled(cron = "0 30 22 * * ?" , zone = "Asia/Tashkent")
    public void runDailyTeachersStatisticsJob() {
        dailyTeachersStatisticsService.scheduleForSaveDailyTeachersStatistics();
    }
}
