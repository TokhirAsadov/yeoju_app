package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeachersStatistics;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseStats3;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetKafedraTeacherStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetDailyTeacherStatistics;
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

    @Override
    public void qaytaSaveQilish(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        call3(year, month, day, week, weekday);
        String date = String.format("%02d.%02d.%d", day, month, year);
        log.info("✅ [MANUAL] Daily teacher statistics saved for date: {}", date);
    }
    private void call3(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
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

                    Optional<DailyTeachersStatistics> optional = dailyTeachersStatisticsRepository.findDailyTeachersStatisticsByTeacherIdAndYearAndMonthAndWeekAndWeekdayAndDay(
                            teacher.getUser().getId(),
                            year,
                            month,
                            week,
                            weekday,
                            day
                    );

                    if (optional.isPresent()) {
                        DailyTeachersStatistics dailyTeachersStatistics = optional.get();
                        dailyTeachersStatistics.setTotalAttended(stats.getTotalAttended());
                        dailyTeachersStatistics.setTotalMissed(stats.getTotalNotAttended());
                        dailyTeachersStatisticsRepository.save(dailyTeachersStatistics);
                    }
                    else {
                        DailyTeachersStatistics entity = new DailyTeachersStatistics(
                                teacher.getUser(),
                                year, month, day, week, weekday,
                                stats.getTotalAttended(),
                                stats.getTotalNotAttended()
                        );

                        dailyTeachersStatisticsRepository.save(entity);
                    }
                    successCount++;

                } catch (Exception e) {
                    failedCount++;
                    log.error("❌ Error saving stats for teacher: {} (kafedra: {})", teacher.getId(), kafedraId, e);
                }
            }

            log.info("✅ Kafedra {}: {} teachers saved successfully, {} failed.", kafedraId, successCount, failedCount);
        });
    }

    @Override
    public ApiResponse getDailyTeacherStatisticsByDay(String teacherId, Integer year, Integer month, Integer day) {
        LocalDate today = LocalDate.now();
        int year2 = today.getYear();
        int month2 = today.getMonthValue();
        int day2 = today.getDayOfMonth();

        if (year==year2 && month==month2 && day==day2){
            ApiResponseStats3 stats = timeTableByWeekOfYearService
                    .getTeacherDailyOrMonthlyStatistics(
                            teacherId,
                            year,
                            month,
                            day
                    );
            return new ApiResponse(true, "Bugungi teacher statistikasi.", stats);
        }
        else {
            GetDailyTeacherStatistics response = dailyTeachersStatisticsRepository.getDailyTeacherStatisticsByDay(teacherId, year, month, day);
            return new ApiResponse(true, String.format("%02d.%02d.%d", day, month, year)+" kungi teacher statistikasi.", response);
        }
    }

    @Override
    public ApiResponse getDailyStatisticsByWeek(String teacherId, Integer year, Integer week) {
        LocalDate today = LocalDate.now();
        int year2 = today.getYear();
        int month2 = today.getMonthValue();
        int day2 = today.getDayOfMonth();
        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week2 = today.get(wf.weekOfYear());
        int weekday2 = today.getDayOfWeek().getValue();

        List<GetDailyTeacherStatistics> response = dailyTeachersStatisticsRepository.getDailyTeacherStatisticsByWeek(teacherId, year, week);
        if (year==year2 && week==week2){
            //todo--- bugungi kungini qo`shish kerak ---
            ApiResponseStats3 stats = timeTableByWeekOfYearService
                    .getTeacherDailyOrMonthlyStatistics(
                            teacherId,
                            year,
                            month2,
                            day2
                    );
            response.add(new GetDailyTeacherStatistics() {
                @Override
                public String getTeacherId() {
                    return teacherId;
                }

                @Override
                public String getId() {
                    return UUID.randomUUID()+"-today";
                }

                @Override
                public Integer getYear() {
                    return year;
                }

                @Override
                public Integer getMonth() {
                    return month2;
                }

                @Override
                public Integer getDay() {
                    return day2;
                }

                @Override
                public Integer getWeek() {
                    return week;
                }

                @Override
                public Integer getWeekday() {
                    return weekday2;
                }

                @Override
                public Integer getTotalAttended() {
                    return stats.getTotalAttended();
                }

                @Override
                public Integer getTotalNotAttended() {
                    return stats.getTotalNotAttended();
                }
            });
        }
        return new ApiResponse(true, year + "-yil "+week+"-haftadagi teacher statistikasi", response);
    }

    @Override
    public ApiResponse getDailyStatisticsByMonth(String teacherId, Integer year, Integer month) {
        LocalDate today = LocalDate.now();
        int year2 = today.getYear();
        int month2 = today.getMonthValue();
        int day2 = today.getDayOfMonth();
        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week2 = today.get(wf.weekOfYear());
        int weekday2 = today.getDayOfWeek().getValue();

        List<GetDailyTeacherStatistics> response = dailyTeachersStatisticsRepository.getDailyTeacherStatisticsByMonth(teacherId, year, month);
        if (year==year2 && month==month2){
            //todo--- bugungi kungini qo`shish kerak ---
            ApiResponseStats3 stats = timeTableByWeekOfYearService
                    .getTeacherDailyOrMonthlyStatistics(
                            teacherId,
                            year,
                            month2,
                            day2
                    );
            response.add(new GetDailyTeacherStatistics() {
                @Override
                public String getTeacherId() {
                    return teacherId;
                }

                @Override
                public String getId() {
                    return UUID.randomUUID()+"-today";
                }

                @Override
                public Integer getYear() {
                    return year;
                }

                @Override
                public Integer getMonth() {
                    return month2;
                }

                @Override
                public Integer getDay() {
                    return day2;
                }

                @Override
                public Integer getWeek() {
                    return week2;
                }

                @Override
                public Integer getWeekday() {
                    return weekday2;
                }

                @Override
                public Integer getTotalAttended() {
                    return stats.getTotalAttended();
                }

                @Override
                public Integer getTotalNotAttended() {
                    return stats.getTotalNotAttended();
                }
            });
        }
        return new ApiResponse(true, year + "-yil "+month+"-oyidagi teacher statistikasi", response);
    }

    @Override
    public ApiResponse getStatisticsForTable(String kafedraId, Integer year, Integer month, Set<String> teachersIds) {
        List<GetKafedraTeacherStatistics> list = dailyTeachersStatisticsRepository.getKafedraTeacherStatistics(kafedraId, year, month, teachersIds);
        return new ApiResponse(true,"xxx", list);
    }


}
