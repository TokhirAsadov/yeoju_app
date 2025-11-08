package uz.yeoju.yeoju_app.controller.timeTableDB;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics.DailyTeachersStatisticsService;


@RestController
@RequestMapping(BaseUrl.BASE_URL+"/daily-teachers-statistics")
@RequiredArgsConstructor
public class DailyTeachersStatisticsController {
    private final DailyTeachersStatisticsService service;

    @GetMapping("/save-by-date")
    public ResponseEntity<ApiResponse> saveByDate(@RequestParam Integer year,
                                                  @RequestParam Integer month,
                                                  @RequestParam Integer day,
                                                  @RequestParam Integer week,
                                                  @RequestParam Integer weekday){
        service.scheduleForSaveDailyTeachersStatisticsByDate(year, month, day, week, weekday);
        return ResponseEntity.ok(new ApiResponse(true, "Statistika saqlandi!"));
    }

    @GetMapping("/getDailyStatisticsByDay/{teacherId}")
    public ResponseEntity<ApiResponse> getDailyTeacherStatisticsByDay(
          @PathVariable String teacherId,
          @RequestParam Integer year,
          @RequestParam Integer month,
          @RequestParam Integer day){
        service.getDailyTeacherStatisticsByDay(teacherId, year, month, day);
        return ResponseEntity.ok(new ApiResponse(true, "Statistika saqlandi!"));
    }
    @GetMapping("/getDailyStatisticsByWeek/{teacherId}")
    public ResponseEntity<ApiResponse> getDailyStatisticsByWeek(
          @PathVariable String teacherId,
          @RequestParam Integer year,
          @RequestParam Integer week){
        ApiResponse response = service.getDailyStatisticsByWeek(teacherId, year, week);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/weekly-statistics")
//    public ResponseEntity<ApiResponse> statistics(@RequestParam Integer year,
//                                                  @RequestParam Integer week,
//                                                  @RequestParam(required = false) String kafedraId){
//        return ResponseEntity.ok(kafedraId == null ?
//                service.getAllKafedrasTeachersStatistics(year, week)
//                : service.getKafedrasTeachersStatistics(year, week, kafedraId));
//    }
//
//    @GetMapping("/statisticsByDay/{day}")
//    public ResponseEntity<ApiResponse> statisticsByDay(@PathVariable String day,
//                                                       @RequestParam(required = false) String kafedraId){
//        return ResponseEntity.ok(service.getKafedrasTeachersStatisticsByDay(day));
//    }
//
//    // GET /api/kafedra-stats/week?start=22.09.2025&end=28.09.2025
//    @GetMapping("/between-days")
//    public ResponseEntity<List<GetKafedraStatistics>> getStatsByWeekRange(
//            @RequestParam("start") String start,
//            @RequestParam("end")   String end) {
//        return service.getByWeekRange(start, end);
//    }
//
//    // GET /api/kafedra-stats/week?start=22.09.2025&end=28.09.2025
//    @GetMapping("/between-days/{kafedraId}")
//    public ResponseEntity<GetKafedraStatistics> getStatsByWeekRangeByKafedraId(
//            @PathVariable("kafedraId") String kafedraId,
//            @RequestParam("start") String start,
//            @RequestParam("end")   String end) {
//        return service.getByWeekRangeByKafedraId(kafedraId,start, end);
//    }
}
