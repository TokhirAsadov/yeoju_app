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

    @GetMapping("/getAllOuterNotifications")
    public HttpEntity<?> getAllOuterNotifications() {
        return ResponseEntity.ok(service.findAllNotifications());
    }

    @GetMapping("/getStudentOuterNotifications/{studentId}")
    public HttpEntity<?> getStudentOuterNotifications(@PathVariable("studentId") String studentId) {
        return ResponseEntity.ok(service.getStudentNotifications(studentId));
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody NotificationOuterCreateDto dto){
        ApiResponse response = service.createAndUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }
    @PutMapping("/update")
    public HttpEntity<?> update(@RequestBody NotificationOuterCreateDto dto){
        ApiResponse response = service.createAndUpdate(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}