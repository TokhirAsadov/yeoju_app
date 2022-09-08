package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.repository.DevicePortRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/admin")
@RequiredArgsConstructor
public class AdminController {
    private final DevicePortRepository devicePortRepository;

    @GetMapping("/menu")
    public HttpEntity<?> getMonitoring(){
        return ResponseEntity.ok(devicePortRepository.getAccMonitoring());
    }

    @GetMapping("/device")
    public HttpEntity<?> device(@CurrentUser User user){
        return ResponseEntity.ok(devicePortRepository.deviceDates(user.getId()));
    }

}
