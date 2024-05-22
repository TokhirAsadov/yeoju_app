package uz.yeoju.yeoju_app.controller.vedimost;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost.VedimostService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/vedimost")
@RequiredArgsConstructor
public class VedimostController {
    private final VedimostService vedimostService;

    @PreAuthorize("hasAnyRole('KAFEDRA','MONITORING','TEACHER')")
    @GetMapping("/getVedimostByTeacherId/{teacherId}")
    public HttpEntity<?> getVedimostByTeacherId(@CurrentUser User user, @PathVariable String teacherId,@RequestParam String educationYearId) {
        return ResponseEntity.ok(vedimostService.getVedimostByTeacherId(teacherId,educationYearId));
    }

    @PreAuthorize("hasRole('KAFEDRA') or hasRole('MONITORING')")
    @GetMapping("/getVedimostByKafedra/{kafedraId}")
    public HttpEntity<?> getVedimostByKafedra(@CurrentUser User user, @PathVariable String kafedraId,@RequestParam String educationYearId) {
        return ResponseEntity.ok(vedimostService.getVedimostByKafedra(kafedraId,educationYearId));
    }

    @PreAuthorize("hasRole('KAFEDRA') or hasRole('MONITORING')")
    @PostMapping("/createVedimost")
    public HttpEntity<?> createVedimost(@CurrentUser User user, @RequestBody VedimostCreaterDto dto) {
        ApiResponse response = vedimostService.createVedimost(dto);
        return ResponseEntity.status(response.isSuccess()?201:401).body(response);
    }
}
