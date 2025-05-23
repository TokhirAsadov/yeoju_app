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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> create(@RequestBody CTestCreator creator){
        ApiResponse response = service.create(creator);
        return ResponseEntity.status(response.isSuccess() ? 201 : 417)
                .body(response);
    }

//    @DeleteMapping("/delete/{id}")
//    HttpEntity<?> delete(@PathVariable String id){
//        boolean deleted = service.de(id);
//        return ResponseEntity.status(deleted ? 200 : 417)
//                .body("Course is deleted.");
//    }

    @GetMapping("/findAll")
    HttpEntity<?> findAll(Pageable pageable){
        ApiResponse res = service.findAll(pageable);
        return ResponseEntity.ok(res);
    }


}
