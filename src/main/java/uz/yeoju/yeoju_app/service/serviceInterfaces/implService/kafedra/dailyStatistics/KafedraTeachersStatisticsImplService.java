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

    @Override
    public void scheduleForSaveDailyStatistics() {
        LocalDate today = LocalDate.now();

        // Yil, oy, kun
        int year = today.getYear();
        int month = today.getMonthValue();   // 1-12
        int day = today.getDayOfMonth();     // 1-31

        String date = (day > 9 ? String.valueOf(day) : "0" + day) + "." + (month > 9 ? String.valueOf(month) : "0" + month) + "." + year;

        // Haftaning tartib raqami (yil bo‘yicha)
        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week = today.get(wf.weekOfYear());

        // Haftaning kuni (Dushanba=1 ... Yakshanba=7)
        int weekday = today.getDayOfWeek().getValue();

        call(year, month, day, week, weekday, date);

        log.info("Kunlik statistika saqlandi: " + date);
    }


    @Override
    public void scheduleForSaveDailyStatisticsByDate(Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        String date = (day > 9 ? String.valueOf(day) : "0" + day) + "." + (month > 9 ? String.valueOf(month) : "0" + month) + "." + year;
        call(year, month, day, week, weekday, date);
        log.info("Statistika saqlandi: " + date);
    }

    @Override
    public ApiResponse getAllKafedrasTeachersStatistics(int year, int week) {
        return new ApiResponse(true, "Success", statisticsRepository.getDaysOfWeek(year, week));
    }

    @Override
    public ApiResponse getKafedrasTeachersStatistics(int year, int week, String kafedraId) {
        System.out.println("kafedraId = " + kafedraId);
        return new ApiResponse(true, "Success", statisticsRepository.getDaysOfWeek2(year, week, kafedraId));
    }

    @Override
    public ApiResponse getKafedrasTeachersStatisticsByDay(String day) {
        return new ApiResponse(true, "Success", statisticsRepository.findStatisticsByDay(day));
    }

    @Override
    public ResponseEntity<List<GetKafedraStatistics>> getByWeekRange(String start, String end) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();   // 1-12
        int day = today.getDayOfMonth();     // 1-31
        String date = (day > 9 ? String.valueOf(day) : "0" + day) + "." + (month > 9 ? String.valueOf(month) : "0" + month) + "." + year;
        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week = today.get(wf.weekOfYear());
        int weekday = today.getDayOfWeek().getValue();

        if (date.equals(end)) {

            List<GetKafedraStatistics> result = new ArrayList<>();
            List<ApiResponseStats2> list = timeTableByWeekOfYearService.getKafedraKunlikVaHaftalikStatistikasi7(null, year, month, day, week, weekday);
            List<GetKafedraStatistics> range = statisticsRepository.findByWeekRange(start, end);
            for (GetKafedraStatistics r : range) {
                for (ApiResponseStats2 res : list) {
                    if (r.getKafedraName().equals(res.getKafedraId())) {

                        result.add(new GetKafedraStatistics() {
                            @Override
                            public String getId() {
                                return r.getId();
                            }

                            @Override
                            public String getKafedraId() {
                                return r.getKafedraId();
                            }

                            @Override
                            public String getKafedraName() {
                                return r.getKafedraName();
                            }

                            @Override
                            public Integer getTotalAttended() {
                                return r.getTotalAttended() + res.getTotalAttended();
                            }

                            @Override
                            public Integer getTotalMissed() {
                                return r.getTotalMissed() + res.getTotalNotAttended();
                            }
                        });
                    }
                }
            }
            return ResponseEntity.ok(result);

        } else {

            return ResponseEntity.ok(statisticsRepository.findByWeekRange(start, end));
        }

    }

    @Override
    public ResponseEntity<GetKafedraStatistics> getByWeekRangeByKafedraId(String kafedraId, String start, String end) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();   // 1-12
        int day = today.getDayOfMonth();     // 1-31
        String date = (day > 9 ? String.valueOf(day) : "0" + day) + "." + (month > 9 ? String.valueOf(month) : "0" + month) + "." + year;
        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week = today.get(wf.weekOfYear());
        int weekday = today.getDayOfWeek().getValue();

        if (date.equals(end)) {
            ApiResponseStats responseStats = timeTableByWeekOfYearService.getKafedraKunlikVaHaftalikStatistikasi6(null, kafedraId, year, month, day, week, weekday);
            GetKafedraStatistics range = statisticsRepository.findByWeekRange(kafedraId, start, end);
            GetKafedraStatistics finalRange = new GetKafedraStatistics() {
                @Override
                public String getId() {
                    return "";
                }

                @Override
                public String getKafedraId() {
                    return kafedraId;
                }

                @Override
                public String getKafedraName() {
                    return range != null ? range.getKafedraName() : "";
                }

                @Override
                public Integer getTotalAttended() {
                    return responseStats.getTotalAttended() + (range != null ? range.getTotalAttended() != null ? range.getTotalAttended() : 0 : 0);
                }

                @Override
                public Integer getTotalMissed() {
                    return responseStats.getTotalNotAttended() + (range != null ? range.getTotalMissed() != null ? range.getTotalMissed() : 0 : 0);
                }
            };
            return ResponseEntity.ok(finalRange);
        } else {
            return ResponseEntity.ok(statisticsRepository.findByWeekRange(kafedraId, start, end));
        }
    }



    private void call(Integer year, Integer month, Integer day, Integer week, Integer weekday, String date) {
        List<ApiResponseStats2> list = timeTableByWeekOfYearService.getKafedraKunlikVaHaftalikStatistikasi7(null, year, month, day, week, weekday);
        if (list.isEmpty()) {
            log.warn("Kafedralar bo'yicha ma'lumotlar topilmadi");
        } else {
            for (ApiResponseStats2 res : list) {
                if (kafedraRepository.existsKafedraByNameEn(res.getKafedraId())) {
                    statisticsRepository.save(new KafedraTeachersStatistics(
                            kafedraRepository.getKafedraByNameEn(res.getKafedraId()),
                            date,
                            res.getTotalAttended(),
                            res.getTotalNotAttended()
                    ));
                } else {
                    log.warn("Kafedra topilmadi: " + res.getKafedraId());
                }
            }
        }
    }
}
