package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateGradesWithThemeDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher.ThemeOfSubjectForGradeByTeacherService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/themeOfSubjectForGrading")
@RequiredArgsConstructor
public class ThemeOfSubjectForGradeByTeacherController {
    private final ThemeOfSubjectForGradeByTeacherService service;

    @PostMapping("/createThemeWithGrade")
    HttpEntity<?> createThemeWithGrade(@CurrentUser User user, @RequestBody CreateGradesWithThemeDto dto){
        ApiResponse themeWithGrade = service.createThemeWithGrade(user, dto);
        return ResponseEntity.status(201).body(themeWithGrade);
    }
}