package uz.yeoju.yeoju_app.controller.uquvbulim;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
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
}
