package uz.yeoju.yeoju_app.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.teacher.TeacherSaveDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher.TeacherService;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/date")
    public void date(@RequestParam("date")
                     @DateTimeFormat(pattern = "yyyy.MM.dd") Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(date + " <------ ");
        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE) + " <- for only this month");
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + " <- new created month ");
        System.out.println(calendar + " <=================================== ");
        System.out.println(Calendar.DATE+"<......................");

    }

    @GetMapping("/changeTeacherPosition")
    public HttpEntity<?> changeTeacherPosition(@RequestParam("userId") String userId,@RequestParam("positionId") Long positionId){
        return ResponseEntity.ok(teacherService.changeTeacherPosition(userId,positionId));
    }

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(teacherService.findAll());
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody TeacherSaveDto dto){
        return ResponseEntity.ok(teacherService.save(dto));
    }
}
