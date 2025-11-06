package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeachersStatistics;
import uz.yeoju.yeoju_app.payload.ApiResponseStats3;
import uz.yeoju.yeoju_app.repository.TeacherRepository;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.DailyTeachersStatisticsRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearService;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DailyTeachersStatisticsServiceImpl implements DailyTeachersStatisticsService {

    private final DailyTeachersStatisticsRepository dailyTeachersStatisticsRepository;
    private final KafedraRepository kafedraRepository;
    private final TeacherRepository teacherRepository;
    private final TimeTableByWeekOfYearService timeTableByWeekOfYearService;

    // 🔹 Global ExecutorService — har safar yangisini yaratmaslik uchun
    private static final ExecutorService executor = Executors.newFixedThreadPool(
            Math.max(4, Runtime.getRuntime().availableProcessors())
    );

    public DailyTeachersStatisticsServiceImpl(
            DailyTeachersStatisticsRepository dailyTeachersStatisticsRepository,
            KafedraRepository kafedraRepository,
            TeacherRepository teacherRepository,
            TimeTableByWeekOfYearService timeTableByWeekOfYearService
    ) {
        this.dailyTeachersStatisticsRepository = dailyTeachersStatisticsRepository;
        this.kafedraRepository = kafedraRepository;
        this.teacherRepository = teacherRepository;
        this.timeTableByWeekOfYearService = timeTableByWeekOfYearService;
    }

    @Override
    public void scheduleForSaveDailyTeachersStatistics() {
        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week = today.get(wf.weekOfYear());
        int weekday = today.getDayOfWeek().getValue();

        call(year, month, day, week, weekday);

        String date = String.format("%02d.%02d.%d", day, month, year);
        log.info("✅ [SCHEDULE] Daily teacher statistics saved for date: {}", date);
    }

    @Override
    public void scheduleForSaveDailyTeachersStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        call(year, month, day, week, weekday);
        String date = String.format("%02d.%02d.%d", day, month, year);
        log.info("✅ [MANUAL] Daily teacher statistics saved for date: {}", date);
    }

    private void call(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        List<String> kafedraIds = kafedraRepository.findAll()
                .stream()
                .map(AbsEntity::getId)
                .collect(Collectors.toList());

        log.info("📊 Total kafedras to process: {}", kafedraIds.size());

        kafedraIds.forEach(kafedraId -> {
            Set<Teacher> teachers = teacherRepository.findAllByKafedraId(kafedraId);
            log.info("➡ Processing kafedra: {} | Teachers found: {}", kafedraId, teachers.size());

            int successCount = 0;
            int failedCount = 0;

            for (Teacher teacher : teachers) {
                try {
                    ApiResponseStats3 stats = timeTableByWeekOfYearService
                            .getTeacherDailyOrMonthlyStatistics(
                                    teacher.getUser().getId(),
                                    year, month, day
                            );

                    DailyTeachersStatistics entity = new DailyTeachersStatistics(
                            teacher.getUser(),
                            year, month, day, week, weekday,
                            stats.getTotalAttended(),
                            stats.getTotalNotAttended()
                    );

                    dailyTeachersStatisticsRepository.save(entity);
                    successCount++;

                } catch (Exception e) {
                    failedCount++;
                    log.error("❌ Error saving stats for teacher: {} (kafedra: {})", teacher.getId(), kafedraId, e);
                }
            }

            log.info("✅ Kafedra {}: {} teachers saved successfully, {} failed.", kafedraId, successCount, failedCount);
        });
    }
    private void call2(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        List<String> kafedraIds = kafedraRepository.findAll()
                .stream()
                .map(AbsEntity::getId)
                .collect(Collectors.toList());

        log.info("📊 Total kafedras to process: {}", kafedraIds.size());

        kafedraIds.forEach(kafedraId -> {
            List<DailyTeachersStatistics> batchList = new ArrayList<>();
            Set<?> teachers = teacherRepository.findAllByKafedraId(kafedraId);

            log.info("➡ Processing kafedra: {} | Teachers found: {}", kafedraId, teachers.size());

            teachers.forEach(t -> {
                try {
                    var teacher = (Teacher) t;

                    ApiResponseStats3 stats = timeTableByWeekOfYearService
                            .getTeacherDailyOrMonthlyStatistics(
                                    teacher.getUser().getId(),
                                    year, month, day
                            );

                    DailyTeachersStatistics entity = new DailyTeachersStatistics(
                            teacher.getUser(),
                            year, month, day, week, weekday,
                            stats.getTotalAttended(),
                            stats.getTotalNotAttended()
                    );

                    batchList.add(entity);

                } catch (Exception e) {
                    log.error("❌ Error while processing teacher: {}", t, e);
                }
            });

            // 🔹 Batch save for performance
            try {
                dailyTeachersStatisticsRepository.saveAll(batchList);
                log.info("✅ Kafedra {}: {} teachers saved successfully.", kafedraId, batchList.size());
            } catch (Exception e) {
                log.error("❌ Error saving batch for kafedra: {}", kafedraId, e);
            }
        });
    }
}
