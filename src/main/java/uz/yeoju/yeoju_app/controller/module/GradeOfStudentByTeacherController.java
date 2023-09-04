package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateGradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeOfStudentByTeacher.GradeOfStudentByTeacherService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/gradeOfStudentByTeacher")
@RequiredArgsConstructor
public class GradeOfStudentByTeacherController {
    private final GradeOfStudentByTeacherService service;

    @GetMapping("/getAllMiddleGrade/{educationYearId}")
    public HttpEntity<?> getAllMiddleGrade(@CurrentUser User user,
                                        @PathVariable("educationYearId") String educationYearId,
                                        @RequestParam("groupId") String groupId
    ){
        return ResponseEntity.ok(service.getAllMiddleGradesOfGroup(educationYearId,groupId));
    }

    @GetMapping("/getMiddleGrade/{educationYearId}")
    public HttpEntity<?> getMiddleGrade(@CurrentUser User user,
                                            @PathVariable("educationYearId") String educationYearId,
                                            @RequestParam("studentId") String studentId,
                                            @RequestParam("subjectId") String subjectId
    ){
        return ResponseEntity.ok(service.getAvgGradesOfStudent(user.getId(),studentId,educationYearId,subjectId));
    }

    @GetMapping("/getGradesOfStudent/{educationYearId}")
    public HttpEntity<?> getGradesOfStudent(@CurrentUser User user,
                                            @PathVariable("educationYearId") String educationYearId,
                                            @RequestParam("studentId") String studentId,
                                            @RequestParam("subjectId") String subjectId
    ){
        return ResponseEntity.ok(service.getGradesOfStudent(user.getId(),studentId,educationYearId,subjectId));
    }

    @PostMapping("/create")
    public HttpEntity<?> create(@CurrentUser User user, @RequestBody CreateGradeOfStudentByTeacher dto){
        ApiResponse apiResponse = service.createGrade(user, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 401).body(apiResponse);
    }
}
