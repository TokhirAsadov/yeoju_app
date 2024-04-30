package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateGradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.payload.module.CreateMultipleGradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeOfStudentByTeacher.GradeOfStudentByTeacherService;

import javax.validation.Valid;
import java.util.Set;

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
                                            @RequestParam("subjectId") String subjectId,
                                            @RequestParam("groupId") String groupId
    ){
        return ResponseEntity.ok(service.getAvgGradesOfStudent(user.getId(),studentId,educationYearId,subjectId,groupId));
    }

    @GetMapping("/getGradesOfStudent/{educationYearId}")
    public HttpEntity<?> getGradesOfStudent(@CurrentUser User user,
                                            @PathVariable("educationYearId") String educationYearId,
                                            @RequestParam("studentId") String studentId,
                                            @RequestParam("subjectId") String subjectId
    ){
        return ResponseEntity.ok(service.getGradesOfStudent(user.getId(),studentId,educationYearId,subjectId));
    }

    @GetMapping("/getRetakesOfStudent/{failGradeId}")
    public HttpEntity<?> getRetakesOfStudent(@CurrentUser User user,
                                            @PathVariable("failGradeId") String failGradeId
    ){
        return ResponseEntity.ok(service.getRetakesOfStudent(failGradeId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/retake")
    public HttpEntity<?> retake(@CurrentUser User user, @RequestBody CreateGradeOfStudentByTeacher dto){
        ApiResponse apiResponse = service.retakeGrade(user, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 401).body(apiResponse);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/create")
    public HttpEntity<?> create(@CurrentUser User user,@Valid @RequestBody CreateGradeOfStudentByTeacher dto){
        ApiResponse apiResponse = service.createGrade(user, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 401).body(apiResponse);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/update")
    public HttpEntity<?> update(@CurrentUser User user,@Valid @RequestBody CreateGradeOfStudentByTeacher dto){
        ApiResponse apiResponse = service.updateGrade(user, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 401).body(apiResponse);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@CurrentUser User user, @PathVariable("id") String id){
        ApiResponse apiResponse = service.delete(user, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 204 : 401).body(apiResponse);
    }
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping(/**/"/multipleUpdate")
    public HttpEntity<?> multipleUpdate(@CurrentUser User user, @Valid @RequestBody Set<CreateMultipleGradeOfStudentByTeacher> dtos){
        ApiResponse apiResponse = service.multipleUpdate(user, dtos);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 401).body(apiResponse);
    }

}
