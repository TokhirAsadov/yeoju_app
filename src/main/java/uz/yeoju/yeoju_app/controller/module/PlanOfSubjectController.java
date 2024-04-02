package uz.yeoju.yeoju_app.controller.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.module.CreatePlanOfStudent;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.planOfSubject.PlanOfSubjectService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/plan")
public class PlanOfSubjectController {

    @Autowired
    private  PlanOfSubjectService service;

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

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/createdPlan")
    public HttpEntity<?> createPlan(@CurrentUser User user, @RequestBody CreatePlanOfStudent dto ){
        //System.out.println(user.toString()+"------------------------111111111111111111111111-----------------");
        System.out.println(dto.toString()+"------------------------111111111111111111111111-----------------");
        return ResponseEntity.ok(service.createPlan(user,dto));
    }
    @PreAuthorize("hasRole('KAFEDRA')")
    @PostMapping("/createdPlanByKafedraMudiri")
    public HttpEntity<?> createdPlanByKafedraMudiri(@CurrentUser User user, @RequestBody CreatePlanOfStudent dto ){
        //System.out.println(user.toString()+"------------------------111111111111111111111111-----------------");
        System.out.println(dto.toString()+"------------------------111111111111111111111111-----------------");
        return ResponseEntity.ok(service.createPlanByKafedraMudiri(user,dto));
    }

    @PreAuthorize("hasRole('TEACHER') OR hasRole('KAFEDRA')")
    @PutMapping("/updatedPlan")
    public HttpEntity<?> updatedPlan(@CurrentUser User user, @RequestBody CreatePlanOfStudent dto ){
        //System.out.println(user.toString()+"------------------------111111111111111111111111-----------------");
        System.out.println(dto.toString()+"------------------------111111111111111111111111-----------------");
        return ResponseEntity.ok(service.updatePlan(user,dto));
    }

}
