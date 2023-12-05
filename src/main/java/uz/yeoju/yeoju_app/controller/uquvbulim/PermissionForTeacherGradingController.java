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



}
