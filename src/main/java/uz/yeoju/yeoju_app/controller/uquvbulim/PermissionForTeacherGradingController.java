package uz.yeoju.yeoju_app.controller.uquvbulim;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreatePermissionForTeacherGradingDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.permissionForTeacherGrading.PermissionForTeacherGradingService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrl.BASE_URL+"/permissionForTeacherGrading")
public class PermissionForTeacherGradingController {
    private final PermissionForTeacherGradingService service;

    @GetMapping("/findAllPermissions")
    public HttpEntity<?> findAllPermissions(){
        return ResponseEntity.ok(service.findAllPermissionsForTeacherGrading());
    }

    @GetMapping("/findAllPermissionsForTeacherGradingByEducationYearIdAndStatus/{educationYearId}")
    public HttpEntity<?> findAllPermissionsForTeacherGradingByEducationYearIdAndStatus(
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("status") String status
    ){
        PPostStatus pPostStatus = PPostStatus.valueOf(status);
        return ResponseEntity.ok(service.findAllPermissionsForTeacherGradingByEducationYearIdAndStatus(educationYearId,pPostStatus));
    }

    @GetMapping("/getHistory")
    public HttpEntity<?> getHistory(){
        return ResponseEntity.ok(service.getHistory());
    }

    @GetMapping("/getConfirmPermission/{educationYearId}")
    public HttpEntity<?> getConfirmPermission(
            @CurrentUser User user,
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("teacherId") String teacherId,
            @RequestParam("subjectId") String subjectId,
            @RequestParam("groupId") String groupId
    ){

        return ResponseEntity.ok(service.getConfirmPermission(user, educationYearId,teacherId, subjectId, groupId));
    }

    @GetMapping("/checkExistsPermission/{educationYearId}")
    public HttpEntity<?> getConfirmPermission(
            @CurrentUser User user,
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("teacherId") String teacherId,
            @RequestParam("subjectId") String subjectId,
            @RequestParam("groupId") String groupId,
            @RequestParam("status") String status

    ){
        return ResponseEntity.ok(service.checkExistsPermission(user, educationYearId,teacherId,subjectId,groupId,PPostStatus.valueOf(status)));
    }


    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable("id") String id){
        ApiResponse response = service.findById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/createPermissionForTeacherGrading")
    public HttpEntity<?> createPermissionForTeacherGrading(@CurrentUser User user, @RequestBody CreatePermissionForTeacherGradingDto dto){
        ApiResponse response = service.createOrUpdatePermissionsForTeacherGrading(user,dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/updatePermissionForTeacherGrading")
    public HttpEntity<?> updatePermissionForTeacherGrading(@CurrentUser User user, @RequestBody CreatePermissionForTeacherGradingDto dto){
        ApiResponse response = service.createOrUpdatePermissionsForTeacherGrading(user,dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
    @PutMapping("/changePermissionStatus")
    public HttpEntity<?> changePermissionStatus(@CurrentUser User user, @RequestBody Set<CreatePermissionForTeacherGradingDto> dtoSet){
        System.out.println(dtoSet);
        Set<ApiResponse> responses = service.changePermissionStatus(user,dtoSet);
        return ResponseEntity.ok(responses);
    }

}
