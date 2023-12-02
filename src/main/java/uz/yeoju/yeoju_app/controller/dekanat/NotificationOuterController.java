package uz.yeoju.yeoju_app.controller.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter.NotificationOuterService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/notificationOuter")
@RequiredArgsConstructor
public class NotificationOuterController {
    private final NotificationOuterService service;

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody NotificationOuterCreateDto dto){
        ApiResponse response = service.createAndUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

}
