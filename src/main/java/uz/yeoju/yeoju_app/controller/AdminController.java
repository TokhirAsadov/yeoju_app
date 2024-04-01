package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.ForUserSaveDto;
import uz.yeoju.yeoju_app.repository.AccDoorRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin.AdminService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AccDoorRepository accDoorRepository;
    private final AdminService adminService;

    @GetMapping("/getInformationAboutCountOfUsers")
    public HttpEntity<?> getInformationAboutCountOfUsers(){
        return ResponseEntity.ok(adminService.getInformationAboutCountOfUsers());
    }

    @GetMapping("/menu")
    public HttpEntity<?> getMonitoring(){
        return ResponseEntity.ok(accDoorRepository.getAccMonitoring());
    }

    @GetMapping("/device")
    public HttpEntity<?> device(@CurrentUser User user){
        return ResponseEntity.ok(accDoorRepository.deviceDates(user.getId()));
    }

    @GetMapping("/userAdd")
    public HttpEntity<?> getForAddUser(@RequestParam("id") String id){
        return ResponseEntity.ok(accDoorRepository.getForAddUser(id));
    }

    @PostMapping("/saveUser")
    public HttpEntity<?> saveUser(@CurrentUser User user,@RequestBody ForUserSaveDto dto){
        ApiResponse response = adminService.saveOrUpdateUser(dto);
        return ResponseEntity.status(response.isSuccess() ? 201:402).body(response);
    }

    @GetMapping("/param")
    public HttpEntity<?> getUserForUpdate(@RequestParam("param") String param){
        return ResponseEntity.ok(adminService.getUserForUpdate(param));
    }

    @GetMapping("/getUserForUpdateById")
    public HttpEntity<?> getUserForUpdateById(@RequestParam("id") String id){
        return ResponseEntity.ok(adminService.getUserForUpdateById(id));
    }

}
