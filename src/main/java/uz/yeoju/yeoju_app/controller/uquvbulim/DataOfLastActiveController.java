package uz.yeoju.yeoju_app.controller.uquvbulim;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository.DataOfLastActiveService;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrl.BASE_URL+"/dataOfLastActive")
public class DataOfLastActiveController {
    private final DataOfLastActiveService service;

    @PreAuthorize("hasRole('ROLE_DEKAN')")
    @GetMapping("/findAll")
    public HttpEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ROLE_MONITORING')")
    @GetMapping("/findByCreatorId/{findByCreatorId}")
    public HttpEntity<?> findByCreatorId(@PathVariable("findByCreatorId") String findByCreatorId) {
        return ResponseEntity.ok(service.findByCreatorId(findByCreatorId));
    }

    @PreAuthorize("hasRole('ROLE_DEKAN')")
    @PostMapping("/create/{passage}")
    public HttpEntity<?> create(@PathVariable("passage") String passage) {
        ApiResponse response = service.create(passage);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }
}
