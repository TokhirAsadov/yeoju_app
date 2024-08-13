package uz.yeoju.yeoju_app.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.test.payload.CreatorEnableTeacherControlTestDto;
import uz.yeoju.yeoju_app.test.service.EnableTeacherControlTestService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/controlTest")
@RequiredArgsConstructor
public class EnableTeacherControlTestController {
    private final EnableTeacherControlTestService service;

    @GetMapping("/findAllEnableTeacher/{kafedraId}")
    @PreAuthorize("hasRole('KAFEDRA')")
    public HttpEntity<?> findAllEnableTeacher(@CurrentUser User user, @PathVariable("kafedraId") String kafedraId) {
        return ResponseEntity.ok(service.findAllEnableTeacherByKafedraId(kafedraId));
    }

    @GetMapping("/getAllTeacherLessons/{teacherId}")
    @PreAuthorize("hasAnyRole('KAFEDRA','TEACHER')")
    public HttpEntity<?> getAllTeacherLessonsByTeacherId(@CurrentUser User user,@PathVariable("teacherId") String teacherId) {
        return ResponseEntity.ok(service.getAllTeacherLessonsByTeacherId(teacherId));
    }

    @PostMapping("/createEnableTeacher")
    @PreAuthorize("hasRole('KAFEDRA')")
    public HttpEntity<?> createEnableTeacher(@CurrentUser User user, @RequestBody CreatorEnableTeacherControlTestDto dto) {
        ApiResponse response = service.createAndUpdateEnableTeacher(dto);
        return ResponseEntity.status(response.isSuccess()?201:401).body(response);
    }
}
