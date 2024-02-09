package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeForAttendance.GradeForAttendanceService;


@RestController
@RequestMapping(BaseUrl.BASE_URL+"/gradeForAttendance")
@RequiredArgsConstructor
public class GradeForAttendanceController {
    private final GradeForAttendanceService service;


}
