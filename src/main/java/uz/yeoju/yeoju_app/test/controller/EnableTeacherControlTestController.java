package uz.yeoju.yeoju_app.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.test.service.EnableTeacherControlTestService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/controlTest")
@RequiredArgsConstructor
public class EnableTeacherControlTestController {
    private final EnableTeacherControlTestService service;

    @GetMapping("/findAllEnableTeacher/{kafedraId}")
    @PreAuthorize("hasRole('ROLE_KAFEDRA')")
    public HttpEntity<?> findAllEnableTeacher(@PathVariable("kafedraId") String kafedraId) {
        return ResponseEntity.ok(service.findAllEnableTeacherByKafedraId(kafedraId));
    }
}
