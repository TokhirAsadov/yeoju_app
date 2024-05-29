package uz.yeoju.yeoju_app.controller.finalGrade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.FinalGradeCreatorDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.finalGrade.FinalGradeOfStudentService;

import javax.validation.Valid;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/finalGrade")
@RequiredArgsConstructor
public class FinalGradeController {
    private final FinalGradeOfStudentService finalGradeOfStudentService;

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/createFinalGrades")
    public HttpEntity<?> createFinalGrades(@CurrentUser User user, @RequestBody @Valid FinalGradeCreatorDto dto) {
        ApiResponse response = finalGradeOfStudentService.createFinalGrades(dto);
        return ResponseEntity.status(response.isSuccess()?201:401).body(response);
    }

    @PreAuthorize("hasAnyRole('KAFEDRA','MONITORING','TEACHER','DEKAN')")
    @GetMapping("/getGradesWithVedimost/{vedimostId}")
    public HttpEntity<?> getGradesWithVedimostByVedimostId(@CurrentUser User user, @PathVariable String vedimostId) {
        ApiResponse response = finalGradeOfStudentService.getGradesWithVedimostByVedimostId(vedimostId);
        return ResponseEntity.status(response.isSuccess()?200:401).body(response);
    }
}
