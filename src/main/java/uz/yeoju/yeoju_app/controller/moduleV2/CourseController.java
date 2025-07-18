package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseUpdator;
import uz.yeoju.yeoju_app.secret.CurrentUser;
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

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> update(@RequestBody CourseUpdator updator){
        ApiResponse response = service.updateCourse(updator);
        return ResponseEntity.status(response.isSuccess() ? 200 : 417)
                .body(response);
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

    @GetMapping("/findByPlanId/{id}")
    HttpEntity<?> findByPlanId(@PathVariable String id){
        ApiResponse res = service.findByPlanId(id);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/findByStudentIdAndEducationYearId/{studentId}/{educationYearId}")
    HttpEntity<?> findByStudentIdAndEducationYearId(@PathVariable String studentId,
                                                    @PathVariable String educationYearId){
        ApiResponse res = service.findByStudentIdAndEducationYearId(studentId,educationYearId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/getCourseProgressForGroup/{groupId}/{courseId}")
    HttpEntity<?> getCourseProgressForGroup(@PathVariable String groupId,
                                                    @PathVariable String courseId){
        ApiResponse res = service.getCourseProgressForGroup(groupId,courseId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/getGroups/{facultyId}/{courseId}")
    HttpEntity<?> getGroups(@PathVariable String facultyId,
                                            @PathVariable String courseId){
        ApiResponse res = service.getGroups(facultyId,courseId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/getCourseByIdV1/{id}")
    HttpEntity<?> getCourseByIdV1(@PathVariable String id){
        ApiResponseTwoObj res = service.getCourseByIdV1(id);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }

    @GetMapping("/getCourseByIdV2/{id}")
    HttpEntity<?> getCourseByIdV1(@CurrentUser User user, @PathVariable String id, @RequestParam(required = false) String userId){
        if (userId == null) {
            ApiResponse res = service.getCourseByIdV2(id,user.getId());
            return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                    .body(res);
        }
        ApiResponse res = service.getCourseByIdV2(id,userId);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }
}
