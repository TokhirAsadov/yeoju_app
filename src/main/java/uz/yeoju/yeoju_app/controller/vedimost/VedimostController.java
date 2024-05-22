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
import uz.yeoju.yeoju_app.payload.module.VedimostUpdaterDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost.VedimostService;

import javax.validation.Valid;

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

    @PreAuthorize("hasAnyRole('KAFEDRA','MONITORING','TEACHER')")
    @GetMapping("/getAllVedimostByTeacherId/{teacherId}")
    public HttpEntity<?> getAllVedimostByTeacherId(@CurrentUser User user, @PathVariable String teacherId) {
        return ResponseEntity.ok(vedimostService.getAllVedimostByTeacherId(teacherId));
    }

    @PreAuthorize("hasAnyRole('KAFEDRA','MONITORING')")
    @GetMapping("/getAllVedimostByKafedra/{kafedraId}")
    public HttpEntity<?> getAllVedimostByKafedra(@CurrentUser User user, @PathVariable String kafedraId) {
        return ResponseEntity.ok(vedimostService.getAllVedimostByKafedra(kafedraId));
    }

    @PreAuthorize("hasRole('KAFEDRA') or hasRole('MONITORING')")
    @GetMapping("/getVedimostByKafedra/{kafedraId}")
    public HttpEntity<?> getVedimostByKafedra(@CurrentUser User user, @PathVariable String kafedraId,@RequestParam String educationYearId) {
        return ResponseEntity.ok(vedimostService.getVedimostByKafedra(kafedraId,educationYearId));
    }
    @PreAuthorize("hasAnyRole('KAFEDRA','MONITORING')")
    @PutMapping("/updateVedimost")
    public HttpEntity<?> updateVedimost(@CurrentUser User user, @RequestBody @Valid VedimostUpdaterDto dto) {
        ApiResponse response = vedimostService.updateVedimost(dto);
        return ResponseEntity.status(response.isSuccess()?201:401).body(response);
    }


    @PreAuthorize("hasRole('KAFEDRA')")
    @PostMapping("/createVedimost")
    public HttpEntity<?> createVedimost(@CurrentUser User user, @RequestBody @Valid VedimostCreaterDto dto) {
        ApiResponse response = vedimostService.createVedimost(dto);
        return ResponseEntity.status(response.isSuccess()?201:401).body(response);
    }

    @PreAuthorize("hasRole('KAFEDRA')")
    @DeleteMapping("/deleteVedimost/{id}")
    public HttpEntity<?> deleteVedimost(@CurrentUser User user, @PathVariable String id) {
        ApiResponse response = vedimostService.deleteVedimostById(id);
        return ResponseEntity.status(response.isSuccess()?201:401).body(response);
    }
}
