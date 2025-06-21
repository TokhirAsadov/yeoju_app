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


}
