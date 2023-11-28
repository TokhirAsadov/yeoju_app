package uz.yeoju.yeoju_app.controller.uquvbulim;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreateAssistant;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository.DataOfLastActiveService;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrl.BASE_URL+"/dataOfLastActive")
public class DataOfLastActiveController {
    private final DataOfLastActiveService service;

    @PreAuthorize("hasRole('ROLE_MONITORING')")
    @GetMapping("/getAssistants")
    public HttpEntity<?> getAssistents(@CurrentUser User user) {
        return ResponseEntity.ok(service.getAssistants());
    }

    @PreAuthorize("hasRole('ROLE_DEKAN') or hasRole('ROLE_MONITORING')")
    @GetMapping("/findAll")
    public HttpEntity<?> findAll(@CurrentUser User user) {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ROLE_MONITORING')")
    @GetMapping("/findByCreatorId/{findByCreatorId}")
    public HttpEntity<?> findByCreatorId(@CurrentUser User user,@PathVariable("findByCreatorId") String findByCreatorId) {
        return ResponseEntity.ok(service.findByCreatorId(findByCreatorId));
    }

    @PreAuthorize("hasRole('ROLE_DEKAN')")
    @PostMapping("/create/{passage}")
    public HttpEntity<?> create(@CurrentUser User user,@PathVariable("passage") String passage) {
        ApiResponse response = service.create(passage);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }
    @PreAuthorize("hasRole('ROLE_MONITORING')")
    @PostMapping("/createAssistant")
    public HttpEntity<?> createAssistant(@CurrentUser User user,@RequestBody() CreateAssistant assistant) {
        ApiResponse response = service.createAssistant(assistant);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }
    @PreAuthorize("hasRole('ROLE_MONITORING')")
    @DeleteMapping("/deleteAssistant/{assistantId}")
    public HttpEntity<?> deleteAssistant(@CurrentUser User user,@PathVariable("assistantId") String assistantId) {
        ApiResponse response = service.deleteAssistant(assistantId);
        return ResponseEntity.status(response.isSuccess() ? 204 : 401).body(response);
    }
}
