package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseCreator;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course.CourseService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/course")
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody CourseCreator creator){
        service.createCourse(creator);
    }

    @DeleteMapping("/delete/{id}")
    HttpEntity<?> delete(@PathVariable String id){
        boolean deleted = service.deleteCourse(id);
        return ResponseEntity.status(deleted ? 200 : 417)
                .body("Course is deleted.");
    }

    @GetMapping("/findAll")
    HttpEntity<?> findAll(Pageable pageable){
        ApiResponse res = service.findAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/findById/{id}")
    HttpEntity<?> findById(@PathVariable String id){
        ApiResponse res = service.findById(id);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/getCourseByIdV1/{id}")
    HttpEntity<?> getCourseByIdV1(@PathVariable String id){
        ApiResponse res = service.getCourseByIdV1(id);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }
}
