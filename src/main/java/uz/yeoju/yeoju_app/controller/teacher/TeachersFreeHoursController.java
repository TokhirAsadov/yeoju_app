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

    @GetMapping("/allFreeHoursForGroup/{educationYearId}/{groupId}")
    public HttpEntity<?> allFreeHoursForGroup(@PathVariable("educationYearId") String educationYearId,@PathVariable("groupId") String groupId){
        return ResponseEntity.ok(service.allFreeHoursForGroup(educationYearId,groupId));
    }

    @GetMapping("/checkerThatExistsTeachersFreeHours/{educationYearId}/{teacherId}")
    public HttpEntity<?> checkerThatExistsTeachersFreeHours(@PathVariable("educationYearId") String educationYearId,@PathVariable("teacherId") String teacherId){
        return ResponseEntity.ok(service.checkerThatExistsTeachersFreeHours(educationYearId,teacherId));
    }
    @GetMapping("/getAllHoursByTeacherId/{teacherId}")
    public HttpEntity<?> getAllHoursByTeacherId(@PathVariable("teacherId") String teacherId){
        return ResponseEntity.ok(service.getAllHoursByTeacherId(teacherId));
    }
    @PostMapping("/createFreeHour")
    public HttpEntity<?> createFreeHour(@CurrentUser User user, @RequestBody TeachersFreeHoursDto dto){
        ApiResponse response = service.createNewHour(user, dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@CurrentUser User user, @PathVariable("id") String id){
        ApiResponse response = service.deleteFreeHours(user,id);
        return ResponseEntity.status(204).body(response);
    }

}
