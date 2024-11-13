package uz.yeoju.yeoju_app.controller.timeTable.changing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherDetailsDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing.TimeTableChangingService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/changingTimeTable")
@RequiredArgsConstructor
public class TimeTableChangingController {
    private final TimeTableChangingService service;

    @PostMapping("/changingTeacherID")
    HttpEntity<?> changingTeacherID(@RequestBody ChangingTeacherDetailsDto dto){
        return ResponseEntity.ok(service.changingTeacherData(dto));
    }

    @GetMapping("/getDataOfTeachers/{weekType}")
    HttpEntity<?> getDataOfTeachers(
            @PathVariable WeekType weekType,
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week
    ){
        return ResponseEntity.ok(service.getDataOfTeachers(weekType, year, week));
    }

}
