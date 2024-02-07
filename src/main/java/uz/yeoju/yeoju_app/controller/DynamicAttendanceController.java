package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dynamicAttendance.DynamicAttendanceService;


@RestController
@RequestMapping(BaseUrl.BASE_URL+"/dynamicAttendance")
@RequiredArgsConstructor
public class DynamicAttendanceController {

    public final DynamicAttendanceService service;

    @PostMapping("/createDynamicAttendance")
    public HttpEntity<?> create(@CurrentUser User user, @RequestBody DynamicAttendanceDto dto){
        ApiResponse response = service.createDynamicAttendance(user, dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }


}
