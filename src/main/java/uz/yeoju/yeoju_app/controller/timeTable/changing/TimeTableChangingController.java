package uz.yeoju.yeoju_app.controller.timeTable.changing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingRoomOfLessonDetailsDto;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherDetailsDto;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherOfLessonDetailsDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing.TimeTableChangingService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/changingTimeTable")
@RequiredArgsConstructor
public class TimeTableChangingController {
    private final TimeTableChangingService service;

    @PostMapping("/changingTeacherOfLesson")
    HttpEntity<?> changingTeacherOfLesson(@RequestBody ChangingTeacherOfLessonDetailsDto dto){
        ApiResponse response = service.changingTeacherOfLesson(dto);
        return ResponseEntity
                .status(response.isSuccess() ? 200 : 400)
                .body(response);
    }

    @PostMapping("/changingRoomOfLesson")
    HttpEntity<?> changingRoomOfLesson(@RequestBody ChangingRoomOfLessonDetailsDto dto){
        return ResponseEntity.ok(service.changingRoomOfLesson(dto));
    }

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

    @GetMapping("/getDataOfRooms/{weekType}")
    HttpEntity<?> getDataOfRooms(
            @PathVariable WeekType weekType,
            @RequestParam("year") Integer year,
            @RequestParam("week") Integer week
    ){
        return ResponseEntity.ok(service.getDataOfRooms(weekType, year, week));
    }

}