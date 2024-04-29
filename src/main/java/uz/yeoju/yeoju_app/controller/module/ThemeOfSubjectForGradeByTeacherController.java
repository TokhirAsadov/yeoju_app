package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping("/getGradesByThemeId/{themeId}")
    public HttpEntity<?> getTableOfGroup(@CurrentUser User user,
                                         @PathVariable("themeId") String themeId
    ){
        return ResponseEntity.ok(service.getGradesByThemeId(themeId));
    }

    @GetMapping("/getTableOfGroup/{educationYearId}/{lessonId}")
    public HttpEntity<?> getTableOfGroup(@CurrentUser User user,
                                           @PathVariable("educationYearId") String educationYearId,
                                           @PathVariable("lessonId") String lessonId,
                                           @RequestParam("groupId") String groupId
    ){
        return ResponseEntity.ok(service.getTableOfGroup(user.getId(),educationYearId,lessonId,groupId));
    }

    @GetMapping("/getThemesWithStudentsGrades/{lessonId}/{groupId}")
    HttpEntity<?> getThemesWithStudentsGrades(@CurrentUser User user,@PathVariable("lessonId") String lessonId,@PathVariable("groupId") String groupId,@RequestParam("educationYearId") String educationYearId){
        return ResponseEntity.ok(service.getThemes(groupId,lessonId,educationYearId, user.getId()));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/createThemeWithGrade")
    HttpEntity<?> createThemeWithGrade(@CurrentUser User user, @RequestBody CreateGradesWithThemeDto dto){
        ApiResponse themeWithGrade = service.createThemeWithGrade(user, dto);
        return ResponseEntity.status(201).body(themeWithGrade);
    }

    @GetMapping("/getThemes/{lessonId}/{groupId}")
    HttpEntity<?> getThemeByLessonIdAndEducationYearIdAndCreatorId(@CurrentUser User user,@PathVariable("lessonId") String lessonId,@PathVariable("groupId") String groupId,@RequestParam("educationYearId") String educationYearId,@RequestParam(name = "t",required = false) String teacherId){
        return ResponseEntity.ok(service.getThemeByLessonIdAndEducationYearIdAndCreatorId(groupId,lessonId,educationYearId, teacherId==null ? user.getId():teacherId));
    }
    @GetMapping("/getLastThemes/{lessonId}/{groupId}")
    HttpEntity<?> getFirstByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(@CurrentUser User user,@PathVariable("lessonId") String lessonId,@PathVariable("groupId") String groupId,@RequestParam("educationYearId") String educationYearId){
        return ResponseEntity.ok(service.getFirstByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(groupId,lessonId,educationYearId, user.getId()));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/createTheme")
    HttpEntity<?> createTheme(@CurrentUser User user, @Valid @RequestBody CreateThemeOfSubjectForGradeDto dto){
        ApiResponse themeWithGrade = service.createTheme(user, dto);
        return ResponseEntity.status(201).body(themeWithGrade);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/updateTheme")
    HttpEntity<?> updateTheme(@CurrentUser User user, @Valid @RequestBody UpdateThemeOfSubjectForGradeDto dto){
        ApiResponse themeWithGrade = service.updateTheme(user, dto);
        return ResponseEntity.status(200).body(themeWithGrade);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/deleteTheme/{themeId}")
    HttpEntity<?> deleteTheme(@CurrentUser User user, @PathVariable("themeId") String themeId){
        ApiResponse themeWithGrade = service.deleteTheme(user, themeId);
        return ResponseEntity.status(204).body(themeWithGrade);
    }
}
