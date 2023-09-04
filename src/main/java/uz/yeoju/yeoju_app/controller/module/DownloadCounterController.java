package uz.yeoju.yeoju_app.controller.module;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.dowloadCounter.DownloadCounterService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/downloadCounter")
@RequiredArgsConstructor
public class DownloadCounterController {
    private final DownloadCounterService service;

    @GetMapping("/getDownloadCountOfOneStudent/{educationYearId}")
    public HttpEntity<?> getDownloadCountOfOneStudent(
            @CurrentUser User user,
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("groupId") String groupId,
            @RequestParam("subjectId") String subjectId,
            @RequestParam("studentId") String studentId
    ){
        return ResponseEntity.ok(service.getDownloadCountOfStudent(user.getId(),educationYearId,groupId,subjectId,studentId));
    }
}
