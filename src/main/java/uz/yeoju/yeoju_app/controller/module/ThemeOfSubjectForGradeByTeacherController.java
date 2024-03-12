package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateGradesWithThemeDto;
import uz.yeoju.yeoju_app.payload.module.CreateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher.ThemeOfSubjectForGradeByTeacherService;

import javax.validation.Valid;

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

    @GetMapping("/getThemes/{lessonId}")
    HttpEntity<?> getThemeByLessonIdAndEducationYearIdAndCreatorId(@CurrentUser User user,@PathVariable("lessonId") String lessonId,@RequestParam("educationYearId") String educationYearId){
        return ResponseEntity.ok(service.getThemeByLessonIdAndEducationYearIdAndCreatorId(lessonId,educationYearId, user.getId()));
    }

    @PostMapping("/createTheme")
    HttpEntity<?> createTheme(@CurrentUser User user, @Valid @RequestBody CreateThemeOfSubjectForGradeDto dto){
        ApiResponse themeWithGrade = service.createTheme(user, dto);
        return ResponseEntity.status(201).body(themeWithGrade);
    }

    @PutMapping("/updateTheme")
    HttpEntity<?> updateTheme(@CurrentUser User user, @Valid @RequestBody UpdateThemeOfSubjectForGradeDto dto){
        ApiResponse themeWithGrade = service.updateTheme(user, dto);
        return ResponseEntity.status(200).body(themeWithGrade);
    }
}
