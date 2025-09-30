package uz.yeoju.yeoju_app.controller.timeTableDB;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraStatistics;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.dailyStatistics.KafedraTeachersStatisticsService;

import java.util.List;


@RestController
@RequestMapping(BaseUrl.BASE_URL+"/kafedra-teachers-statistics")
@RequiredArgsConstructor
public class KafedraTeachersStatisticsController {
    private final KafedraTeachersStatisticsService service;

    @GetMapping("/save-by-date")
    public ResponseEntity<ApiResponse> saveByDate(@RequestParam Integer year,
                                                  @RequestParam Integer month,
                                                  @RequestParam Integer day,
                                                  @RequestParam Integer week,
                                                  @RequestParam Integer weekday){
        service.scheduleForSaveDailyStatisticsByDate(year, month, day, week, weekday);
        return ResponseEntity.ok(new ApiResponse(true, "Statistika saqlandi!"));
    }


    @GetMapping("/weekly-statistics")
    public ResponseEntity<ApiResponse> statistics(@RequestParam Integer year,
                                                  @RequestParam Integer week,
                                                  @RequestParam(required = false) String kafedraId){
        return ResponseEntity.ok(kafedraId == null ?
                service.getAllKafedrasTeachersStatistics(year, week)
                : service.getKafedrasTeachersStatistics(year, week, kafedraId));
    }



}
