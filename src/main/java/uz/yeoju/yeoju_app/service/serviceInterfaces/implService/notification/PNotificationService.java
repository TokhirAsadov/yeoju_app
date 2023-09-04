package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notification;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.payload.resDto.notification.NotificationResDto;

import java.util.List;

public interface PNotificationService {

     Flux<ServerSentEvent<List<PNotification>>> getNotificationsForAdmin(String userId);
     Flux<ServerSentEvent<List<NotificationResDto>>> getNotificationsForAdmin2(String userId);

    List<PNotification> getNotifs(String userID);
    List<NotificationResDto> getNotifs2(String userID);

    PNotification save(PNotification notification);

}
