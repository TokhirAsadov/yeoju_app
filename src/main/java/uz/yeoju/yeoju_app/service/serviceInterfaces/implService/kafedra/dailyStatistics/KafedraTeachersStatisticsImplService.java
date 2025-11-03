package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.timetableDB.KafedraTeachersStatistics;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseStats;
import uz.yeoju.yeoju_app.payload.ApiResponseStats2;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraStatistics;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.KafedraTeachersStatisticsRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearService;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class KafedraTeachersStatisticsImplService implements KafedraTeachersStatisticsService {
    private final KafedraTeachersStatisticsRepository statisticsRepository;
    private final TimeTableByWeekOfYearService timeTableByWeekOfYearService;
    private final KafedraRepository kafedraRepository;

    public KafedraTeachersStatisticsImplService(KafedraTeachersStatisticsRepository statisticsRepository, TimeTableByWeekOfYearService timeTableByWeekOfYearService, KafedraRepository kafedraRepository) {
        this.statisticsRepository = statisticsRepository;
        this.timeTableByWeekOfYearService = timeTableByWeekOfYearService;
        this.kafedraRepository = kafedraRepository;
    }


}
