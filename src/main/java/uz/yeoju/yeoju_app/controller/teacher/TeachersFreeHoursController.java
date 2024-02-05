package uz.yeoju.yeoju_app.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher.TeacherService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/teachersFreeHours")
public class TeachersFreeHoursController {
    @Autowired
    private TeacherService service;


}
