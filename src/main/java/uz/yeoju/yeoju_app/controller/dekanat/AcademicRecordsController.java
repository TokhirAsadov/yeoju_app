package uz.yeoju.yeoju_app.controller.dekanat;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.academicRecords.AcademicRecordsService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/academic-records")
public class AcademicRecordsController {
    private final AcademicRecordsService service;

    public AcademicRecordsController(AcademicRecordsService service) {
        this.service = service;
    }

    @PostMapping("/uploadAll")
    @PreAuthorize("hasAnyRole('ROLE_DEKAN', 'ROLE_ADMIN', 'ROLE_TEACHER')")
    public HttpEntity<?> uploadAll(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        System.out.println(" ----------------------------- 1 1 1 ------------------------ --");
        ApiResponse apiResponse = service.saveRecords(request);
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getRecordsByUserId/{userId}")
    HttpEntity<?> getRecordsByUserId(@CurrentUser User user, @PathVariable("userId") String userId) {
        ApiResponse apiResponse = service.getRecordsByUserId(userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getRecordsByQaydRaqam/{qaydRaqam}")
    HttpEntity<?> getRecordsByQaydRaqam(@CurrentUser User user, @PathVariable("qaydRaqam") String qaydRaqam) {
        ApiResponse apiResponse = service.getRecordsByQaytRaqami(qaydRaqam);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
