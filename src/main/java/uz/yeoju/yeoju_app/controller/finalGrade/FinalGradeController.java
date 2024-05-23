package uz.yeoju.yeoju_app.controller.finalGrade;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.finalGrade.FinalGradeOfStudentService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/finalGrade")
@RequiredArgsConstructor
public class FinalGradeController {
    private final FinalGradeOfStudentService finalGradeOfStudentService;
}
