package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.timetableDB.DailyTeachersStatisticsRepository;

@Service
public class DailyTeachersStatisticsServiceImpl implements DailyTeachersStatisticsService {
    private final DailyTeachersStatisticsRepository dailyTeachersStatisticsRepository;

    public DailyTeachersStatisticsServiceImpl(DailyTeachersStatisticsRepository dailyTeachersStatisticsRepository) {
        this.dailyTeachersStatisticsRepository = dailyTeachersStatisticsRepository;
    }
}
