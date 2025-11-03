package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatisticsScheduler {
    private final KafedraTeachersStatisticsService kafedraTeachersStatisticsService;

    public StatisticsScheduler(KafedraTeachersStatisticsService kafedraTeachersStatisticsService) {
        this.kafedraTeachersStatisticsService = kafedraTeachersStatisticsService;
    }

    @Scheduled(cron = "0 0 22 * * ?" , zone = "Asia/Tashkent")
    public void runDailyStatisticsJob() {
        kafedraTeachersStatisticsService.scheduleForSaveDailyStatistics();
    }
}
