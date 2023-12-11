package uz.yeoju.yeoju_app.controller.timeTable;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearService;

import java.util.Optional;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/timeTableByWeekOfYear")
@RequiredArgsConstructor
public class TimeTableByWeekOfYearController {

    private final TimeTableByWeekOfYearService service;
    private final UserRepository userRepository;

    @GetMapping("/studentStatisticsWithWeekOfEduYear/{eduYearId}")
    public HttpEntity<?> getStudentStatisticsWithWeekOfEdu(
            @PathVariable String eduYearId,
            @RequestParam("facultyId") String facultyId,
            @RequestParam("eduType") String eduType,
            @RequestParam("eduTypeId") String eduTypeId,
            @RequestParam("facultyShortName") String facultyShortName
    ){
        return ResponseEntity.ok(service.getStatisticsForDeanDashboard(eduYearId,eduType,eduTypeId,facultyId,facultyShortName));
    }


    @GetMapping("/getItem")
    public HttpEntity<?> getItem(@RequestParam("week") Integer week){
        return ResponseEntity.ok(new ApiResponse(true,"ishlayapdi"));
    }

    @GetMapping("/studentTimeTable/{groupName}")
    public HttpEntity<?> studentTimeTableAPI(
            @CurrentUser User user,
            @PathVariable String groupName,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day,
            @RequestParam(name = "s") Boolean s
    ) {
        System.out.println(week+" <- week");
        return ResponseEntity.ok(service.getStudentTimeTableAPIByWeekOfYear(user,groupName,year,week,day,s));
    }

    @GetMapping("/studentTimeTable/{groupName}/{userId}")
    public HttpEntity<?> studentTimeTableAPI(
            @PathVariable String groupName,
            @PathVariable String userId,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day,
            @RequestParam(name = "s") Boolean s
    ) {
        System.out.println(week+" <- week");
        return ResponseEntity.ok(service.getStudentTimeTableAPIByWeekOfYear(userId,groupName,year,week,day,s));
    }

    @GetMapping("/studentTimeTableByWeek/{groupName}")
    public HttpEntity<?> studentTimeTableByWeek(
            @CurrentUser User user,
            @PathVariable String groupName,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day,
            @RequestParam(name = "s") Boolean s
    ) {
        return ResponseEntity.ok(service.getTimesForRoomStatisticsByUserIdAndWeek(user,groupName,year,week,day,s));
    }

    @GetMapping("/getTeacherTimeTable")
    public HttpEntity<?> getTeacherTimeTable(@CurrentUser User user,@RequestParam(name = "t",required = false) String teacherId, @RequestParam("week") Integer week,@RequestParam("year") Integer year){
        if (teacherId==null) {
            return ResponseEntity.ok(service.getTeacherTimeTable(user,week,year));
        }
        else {
            Optional<User> userOptional = userRepository.findById(teacherId);
            return userOptional.map(value -> ResponseEntity.ok(service.getTeacherTimeTable(value,week,year))).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "error... not fount user...")));
        }
    }


    @GetMapping("/getTeacherTimeTableToday")
    public HttpEntity<?>oneDayForTeacher(@CurrentUser User user,@RequestParam(name = "t",required = false) String teacherId, @RequestParam("week") Integer week,@RequestParam("year") Integer year){
        if (teacherId==null) {
            return ResponseEntity.ok(service.getTeacherTimeTableToday(user,week,year));
        }
        else {
            Optional<User> userOptional = userRepository.findById(teacherId);
            return userOptional.map(value -> ResponseEntity.ok(service.getTeacherTimeTableToday(value,week,year))).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "error... not fount user...")));
        }
    }

    @GetMapping("/getTimeTableByRoom")
    public HttpEntity<?> getTimeTableByRoom(@CurrentUser User user,
                                            @RequestParam("r") String room,
                                            @RequestParam("weekday") Integer weekday,
                                            @RequestParam("week") Integer week,
                                            @RequestParam("year") Integer year
    ) {
        return ResponseEntity.ok(service.getTimeTableByRoomAndWeek(user,room,weekday,week,year));
    }

    @GetMapping("/getTimeTableByAllRoomAndWeek")
    public HttpEntity<?> getTimeTableByAllRoomAndWeek(@CurrentUser User user,
                                            @RequestParam("b") String building,
                                            @RequestParam("weekday") Integer weekday,
                                            @RequestParam("week") Integer week,
                                            @RequestParam("year") Integer year,
                                            @RequestParam("t") Boolean t
    ) {
        return ResponseEntity.ok(service.getTimeTableByAllRoomAndWeek(user,building,weekday,week,year,t));
    }

    @GetMapping("/getTimeTableByAllRoomAndWeek2")
    public HttpEntity<?> getTimeTableByAllRoomAndWeek2(@CurrentUser User user,
                                                      @RequestParam("weekday") Integer weekday,
                                                      @RequestParam("week") Integer week,
                                                      @RequestParam("year") Integer year,
                                                      @RequestParam("t") Boolean t
    ) {
        return ResponseEntity.ok(service.getTimeTableByAllRoomAndWeek2(user,weekday,week,year,t));
    }


    @GetMapping("/getTeacherTimeTableAndStatisticsForKafedra")
    public HttpEntity<?> getTeacherTimeTableAndStatisticsForKafedra(@CurrentUser User user,
                                                       @RequestParam("kafedraId") String kafedraId,
                                                       @RequestParam("year") Integer year,
                                                       @RequestParam("month") Integer month,
                                                       @RequestParam("day") Integer day,
                                                       @RequestParam("weekday") Integer weekday,
                                                       @RequestParam("week") Integer week
    ) {
        return ResponseEntity.ok(service.getTeacherTimeTableAndStatisticsForKafedra(user,kafedraId,year,month,day,week,weekday));
    }

}
