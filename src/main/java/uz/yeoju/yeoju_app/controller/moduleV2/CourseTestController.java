package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CTestCreator;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.courseTest.CourseTestService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/course-test")
public class CourseTestController {
    private final CourseTestService service;

    public CourseTestController(CourseTestService service) {
        this.service = service;
    }

    
}
