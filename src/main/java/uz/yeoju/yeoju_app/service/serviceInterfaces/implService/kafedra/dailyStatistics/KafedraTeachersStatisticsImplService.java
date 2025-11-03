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
