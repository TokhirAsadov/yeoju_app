package uz.yeoju.yeoju_app.controller.kafedra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSave;
import uz.yeoju.yeoju_app.payload.dekanat.StudentChangeDto;
import uz.yeoju.yeoju_app.payload.kafedra.ChangeKafedraNameDto;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraMudiriSaving;
import uz.yeoju.yeoju_app.payload.kafedra.TeacherEditDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.KafedraMudiriService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/kafera-mudiri")
public class KafedraMudiriController {

    @Autowired
    private KafedraMudiriService service;


    @GetMapping("/getTeachersStatisticsForKafedraDashboard")
    public HttpEntity<?> getTeachersStatisticsForKafedraDashboard(@CurrentUser User user,@RequestParam(name = "kafedraId",required = false) String kafedraId){
        return  ResponseEntity.ok(service.getTeachersStatisticsForKafedraDashboard(kafedraId));
    }


    @PostMapping("/changeTeacher")
    public HttpEntity<?> changeStudent(@RequestBody TeacherEditDto dto){
        return ResponseEntity.status(201).body(service.changeTeacher(dto));
    }
    @PutMapping("/changeNameOfKafedra")
    public HttpEntity<?> changeNameOfKafedra(@CurrentUser User user,@RequestBody ChangeKafedraNameDto dto){
        ApiResponse response = service.changeNameOfKafedra(dto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 403).body(response);
    }

    @PutMapping("/changeRoomOfKafedra")
    public HttpEntity<?> changeRoomOfKafedra(@CurrentUser User user,@RequestBody ChangeKafedraNameDto dto){
        ApiResponse response = service.changeRoomOfKafedra(dto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 403).body(response);
    }

    @GetMapping("/positionEdit")
    public HttpEntity<?> positionEdit(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(service.positionEdit(user.getId(),id));
    }


    @PostMapping("/saveKafedra")
    public HttpEntity<?> saveKafedra(@RequestBody DekanSave dto){
        return ResponseEntity.status(201).body(service.saveKafedra(dto));
    }

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody KafedraMudiriSaving saving){
        return ResponseEntity.ok(service.save(saving));
    }

    @GetMapping("/getStatistics")
    public HttpEntity<?> getStatistics(@CurrentUser User user,@RequestParam(name = "id") String kafedraId) {
        return ResponseEntity.ok(service.getStatistics(kafedraId));
//        return ResponseEntity.ok(service.getStatisticsOneLesson(user));
    }

    @GetMapping("/getStatisticss")
    public HttpEntity<?> getStatistics(@CurrentUser User user,@RequestParam("userId") String userId,@RequestParam("date")
    @DateTimeFormat(pattern = "yyyy.MM.dd") Date date)
    {
        return ResponseEntity.ok(service.getStatistics(user,userId,date)/*new ApiResponse(false,"texnik iwlar olib borilyapdi")*/);
    }

    @GetMapping("/getStatisticsForTable")
    public HttpEntity<?> getStatisticsForTable(
            @CurrentUser User user,
            @RequestParam("kafedraId") String kafedraId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy.MM.dd") Date date,
            @RequestParam("teachersIds") Set<String> teachersIds
    )
    {
        System.out.println(kafedraId);
        System.out.println(date);
        System.out.println(teachersIds);
        return ResponseEntity.ok(service.getStatisticsForTable(kafedraId,date,teachersIds)/*new ApiResponse(false,"texnik iwlar olib borilyapdi")*/);
    }

    @GetMapping("/getStatisticssForRektor")
    public HttpEntity<?> getStatisticssForRektor(@CurrentUser User user,@RequestParam("kafedraId") String kafedraId,@RequestParam("date")
    @DateTimeFormat(pattern = "yyyy.MM.dd") Date date)
    {
        return ResponseEntity.ok(service.getStatisticsForRektor(kafedraId,date)/*new ApiResponse(false,"texnik iwlar olib borilyapdi")*/);
    }

    @DeleteMapping("/deletedTeacherWithUserId/{id}")
    public HttpEntity<?> deletedTeacherWithUserId(@PathVariable("id") String id){
        return ResponseEntity.ok(service.deletedTeacherWithUserId(id));
    }

    @GetMapping("/changeRoles")
    public HttpEntity<?> changeRolesTeachers(){
        return ResponseEntity.ok(service.changeRolesTeachers());
    }

//    @PostMapping("/date")
//    public HttpEntity<?> date(@RequestParam("date")
//                     @DateTimeFormat(pattern = "yyyy.MM.dd") Date date) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//
//    }
}
