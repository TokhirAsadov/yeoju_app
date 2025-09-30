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



}
