package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.moduleV2.CreatePlanOfStudentV2;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.planOfSubject.PlanOfSubjectV2Service;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/planV2")
public class PlanOfSubjectV2Controller {

    @Autowired
    private PlanOfSubjectV2Service service;

    // module
    @GetMapping("/getTeacherSubjects/{educationYearId}")
    public HttpEntity<?> getTeacherSubjects(@CurrentUser User user,
                                            @PathVariable("educationYearId") String educationYearId,
                                            @RequestParam(value = "teacherId", required = false) String teacherId
    ){
        if (teacherId != null && !teacherId.isEmpty()) {
            return ResponseEntity.ok(service.getTeacherSubjects(teacherId, educationYearId));
        }
        return ResponseEntity.ok(service.getTeacherSubjects(user.getId(),educationYearId));
    }

    @GetMapping("/getPlansBySubjectId/{lessonId}/{educationYearId}")
    public HttpEntity<?> getPlansBySubjectId(@CurrentUser User user,
                                            @PathVariable("lessonId") String lessonId,
                                            @PathVariable("educationYearId") String educationYearId

    ){
        return ResponseEntity.ok(service.getPlansBySubjectId(lessonId,educationYearId));
    }

    @GetMapping("/getTeacherWIthSubjectForPlan/{educationYearId}")
    public HttpEntity<?> getTeacherWIthSubjectForPlan(@CurrentUser User user,@PathVariable("educationYearId") String educationYearId){
        return ResponseEntity.ok(service.getTeacherWIthSubjectForPlan(user.getId(),educationYearId));
    }

    @GetMapping("/getPlansForTeacherSciences")
    public HttpEntity<?> getPlansForTeacherSciences(@CurrentUser User user,
                                                    @RequestParam("educationYearId") String educationYearId,
                                                    @RequestParam("subjectId") String subjectId,
                                                    @RequestParam("level") Integer level
                                                    ){
        return ResponseEntity.ok(service.getPlansForTeacherSciences(user.getId(),educationYearId, subjectId, level));
    }

    @GetMapping("/getAllPlansForTeacherSciences")
    public HttpEntity<?> getAllPlansForTeacherSciences(@CurrentUser User user,
                                                    @RequestParam("educationYearId") String educationYearId
    ){
        return ResponseEntity.ok(service.getAllPlansForTeacherSciences(user.getId(),educationYearId));
    }

    @GetMapping("/getExistPlans")
    public HttpEntity<?> getExistPlans(@CurrentUser User user,
                                                    @RequestParam("educationYearId") String educationYearId,
                                                    @RequestParam("subjectId") String subjectId,
                                                    @RequestParam("level") Integer level
    ){
        return ResponseEntity.ok(service.getExistPlans(user.getId(),educationYearId, subjectId, level));
    }

//    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/createdPlan")
    public HttpEntity<?> createPlan(@CurrentUser User user, @RequestBody CreatePlanOfStudentV2 dto ){
        return ResponseEntity.ok(service.createPlan(user,dto));
    }

    @GetMapping("/getPlansByKafedraId/{kafedraId}")
    public HttpEntity<?> getPlansByKafedraId(@CurrentUser User user,
                                       @PathVariable("kafedraId") String kafedraId
    ){
        return ResponseEntity.ok(service.getPlansByKafedraId(user.getId(), kafedraId));
    }

    @GetMapping("/getAllDataOfPlanById/{planId}")
    HttpEntity<?> getAllDataOfPlanById(@PathVariable("planId") String planId){
        return ResponseEntity.ok(service.getAllDataOfPlanById(planId));
    }

    @GetMapping("/getCourseDetailsByCourseId/{courseId}")
    HttpEntity<?> getCourseDetailsByCourseId(@PathVariable("courseId") String courseId){
        return ResponseEntity.ok(service.getCourseDetailsByCourseId(courseId));
    }

}
