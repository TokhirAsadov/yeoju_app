package uz.yeoju.yeoju_app.controller.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.payload.resDto.notification.NotificationResDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notification.PNotificationService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final PNotificationService notificationService;

    @GetMapping("/stream")
    public Flux<ServerSentEvent<List<PNotification>>> streamNotificationForAdmin(@RequestParam(required = false,name="userId") String userId) {
        return notificationService.getNotificationsForAdmin(userId);
    }
    @GetMapping("/stream2")
    public Flux<ServerSentEvent<List<NotificationResDto>>> streamNotificationForAdmin2(@RequestParam(required = false,name="userId") String userId) {
        return notificationService.getNotificationsForAdmin2(userId);
    }
}
