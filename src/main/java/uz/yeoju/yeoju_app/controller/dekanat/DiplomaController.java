package uz.yeoju.yeoju_app.controller.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DiplomaCreator;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma.DiplomaService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/diploma")
@RequiredArgsConstructor
public class DiplomaController {
    private final DiplomaService service;

    @PostMapping("/create")
    @PreAuthorize("hasRole('DEKAN')")
    HttpEntity<?> createDiploma(@CurrentUser User user, @RequestBody DiplomaCreator creator){
        ApiResponse response = service.createDiploma(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('DEKAN')")
    HttpEntity<?> updateDiploma(@CurrentUser User user, @RequestBody DiplomaCreator creator){
        ApiResponse response = service.updateDiploma(creator);
        return ResponseEntity.status(response.isSuccess() ? 203 : 409).body(response);
    }

    @PostMapping("/uploadDiploma")
    @PreAuthorize("hasRole('DEKAN')")
    HttpEntity<?> uploadDiploma(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        ApiResponse apiResponse = service.uploadDiploma(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/getStudentsWithDiploma/${groupId}")
    @PreAuthorize("hasRole('DEKAN')")
    HttpEntity<?> getStudentsWithDiploma(@PathVariable String groupId){
        ApiResponse response = service.getStudentsWithDiploma(groupId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/deleteDiploma/${id}")
    @PreAuthorize("hasRole('DEKAN')")
    HttpEntity<?> deleteDiploma(@PathVariable String id){
        ApiResponse response = service.deleteDiploma(id);
        return ResponseEntity.status(response.isSuccess() ? 204 : 409).body(response);
    }
}
