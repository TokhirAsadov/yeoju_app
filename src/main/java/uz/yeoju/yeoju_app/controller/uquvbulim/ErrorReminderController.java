package uz.yeoju.yeoju_app.controller.uquvbulim;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder.ErrorReminderService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/errorReminder")
@RequiredArgsConstructor
public class ErrorReminderController {
    private final ErrorReminderService service;

    @PreAuthorize("hasAnyRole('TEACHER','REKTOR','KAFEDRA','DEKAN','MONITORING')")
    @GetMapping("/findAllErrorData")
    public HttpEntity<?> findAllErrorData(@CurrentUser User user) {
        return ResponseEntity.ok(service.getAllErrorsForSpecialUser(user));
    }
}
