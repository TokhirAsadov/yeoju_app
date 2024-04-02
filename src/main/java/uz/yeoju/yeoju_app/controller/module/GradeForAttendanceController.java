package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.GradeForAttendanceDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeForAttendance.GradeForAttendanceService;


@RestController
@RequestMapping(BaseUrl.BASE_URL+"/gradeForAttendance")
@RequiredArgsConstructor
public class GradeForAttendanceController {
    private final GradeForAttendanceService service;


    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public HttpEntity<?> create(@CurrentUser User user, @RequestBody GradeForAttendanceDto dto){
        ApiResponse apiResponse = service.createGradeForAttendance(user, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 401).body(apiResponse);
    }
}
