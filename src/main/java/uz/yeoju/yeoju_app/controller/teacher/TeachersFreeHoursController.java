package uz.yeoju.yeoju_app.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.teacher.TeachersFreeHoursDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.teachersFreeHours.TeachersFreeHoursService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/teachersFreeHours")
public class TeachersFreeHoursController {
    @Autowired
    private TeachersFreeHoursService service;

    @GetMapping("/checkerThatExistsTeachersFreeHours/{educationYearId}/{teacherId}")
    public HttpEntity<?> checkerThatExistsTeachersFreeHours(@PathVariable("educationYearId") String educationYearId,@PathVariable("teacherId") String teacherId){
        return ResponseEntity.ok(service.checkerThatExistsTeachersFreeHours(educationYearId,teacherId));
    }

}
