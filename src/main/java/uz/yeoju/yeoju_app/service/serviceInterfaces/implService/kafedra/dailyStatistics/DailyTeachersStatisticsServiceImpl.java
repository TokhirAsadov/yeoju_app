package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeachersStatistics;
import uz.yeoju.yeoju_app.payload.ApiResponseStats3;
import uz.yeoju.yeoju_app.repository.TeacherRepository;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.DailyTeachersStatisticsRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyTeachersStatisticsServiceImpl implements DailyTeachersStatisticsService {
    private final DailyTeachersStatisticsRepository dailyTeachersStatisticsRepository;
    private final KafedraRepository kafedraRepository;
    private final TeacherRepository teacherRepository;
    private final TimeTableByWeekOfYearService timeTableByWeekOfYearService;

    public DailyTeachersStatisticsServiceImpl(DailyTeachersStatisticsRepository dailyTeachersStatisticsRepository, KafedraRepository kafedraRepository, TeacherRepository teacherRepository, TimeTableByWeekOfYearService timeTableByWeekOfYearService) {
        this.dailyTeachersStatisticsRepository = dailyTeachersStatisticsRepository;
        this.kafedraRepository = kafedraRepository;
        this.teacherRepository = teacherRepository;
        this.timeTableByWeekOfYearService = timeTableByWeekOfYearService;
    }

    @Override
    public void scheduleForSaveDailyTeachersStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        List<String> kafedrasIds = kafedraRepository.findAll().stream().map(AbsEntity::getId).collect(Collectors.toList());
        kafedrasIds.forEach(kafedrasId -> {
            teacherRepository.findAllByKafedraId(kafedrasId).forEach(teacher -> {
                ApiResponseStats3 statistics = timeTableByWeekOfYearService.getTeacherDailyOrMonthlyStatistics(teacher.getUser().getId(), year, month, day);
                dailyTeachersStatisticsRepository.save(new DailyTeachersStatistics(
                        teacher.getUser(),
                        year,
                        month,
                        day,
                        week,
                        weekday,
                        statistics.getTotalAttended(),
                        statistics.getTotalNotAttended()
                ));
            });
        });
        String date = (day > 9 ? String.valueOf(day) : "0" + day) + "." + (month > 9 ? String.valueOf(month) : "0" + month) + "." + year;
        System.out.println("::::::::: "+date+" :::::::::");
    }
}
