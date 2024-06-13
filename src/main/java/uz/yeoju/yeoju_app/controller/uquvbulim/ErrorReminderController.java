package uz.yeoju.yeoju_app.controller.uquvbulim;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder.ErrorReminderService;

import javax.validation.Valid;

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

    @PreAuthorize("hasAnyRole('TEACHER','REKTOR','KAFEDRA','DEKAN','MONITORING')")
    @GetMapping("/changeActivityOfError")
    public HttpEntity<?> changeActivityOfError(@CurrentUser User user,@RequestParam(name = "id",required = false) String id,@RequestParam(name = "type") @Valid String type) {
        ApiResponse response = service.changeActivityOfError(user, type, id);
        return ResponseEntity.status(response.isSuccess()?200:400).body(response);
    }
}
