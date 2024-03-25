package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dynamicAttendance.DynamicAttendanceService;

import javax.validation.Valid;
import java.util.Set;


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
    @PutMapping("/updateDynamicAttendance")
    public HttpEntity<?> update(@CurrentUser User user,@Valid @RequestBody DynamicAttendanceDto dto){
        ApiResponse response = service.createDynamicAttendance(user, dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }
    @PostMapping("/createMultiDynamicAttendance")
    public HttpEntity<?> createMulti(@CurrentUser User user, @RequestBody MultiDynamicAttendanceDto dto){
        ApiResponse response = service.createMultiDynamicAttendance(user, dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @PostMapping("/createMultiDynamicAttendance2")
    public HttpEntity<?> createMulti2(@CurrentUser User user, @RequestBody MultiDynamicAttendance2Dto dto){
        ApiResponse response = service.createMultiDynamicAttendance2(user, dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @PostMapping("/createMultiDynamicAttendance3")
    public HttpEntity<?> createMulti3(@CurrentUser User user, @RequestBody Set<MultiDynamicAttendance3Dto> dtoes){
        ApiResponse response = service.createMultiDynamicAttendance3(user, dtoes);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @PutMapping("/updateMultiDynamicAttendance3")
    public HttpEntity<?> updateMulti3(@CurrentUser User user, @RequestBody Set<MultiDynamicAttendance3Dto> dtoes){
        ApiResponse response = service.updateMultiDynamicAttendance3(user, dtoes);
        return ResponseEntity.status(response.isSuccess() ? 200:402).body(response);
    }

    @PutMapping("/updateMultiDynamicAttendance")
    public HttpEntity<?> updateMultiDynamicAttendance(@CurrentUser User user, @RequestBody Set<UpdateMultiDynamicAttendanceDto> dtos){
        ApiResponse response = service.updateMultiDynamicAttendance(user, dtos);
        return ResponseEntity.status(200).body(response);
    }
}
