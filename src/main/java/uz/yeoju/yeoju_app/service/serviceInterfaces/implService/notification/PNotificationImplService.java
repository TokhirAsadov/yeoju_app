package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notification;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.repository.permissionPost.PNotificationRepository;
import uz.yeoju.yeoju_app.repository.permissionPost.PermissionPostRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PNotificationImplService implements PNotificationService{
    private final PNotificationRepository notificationRepository;
    private final PermissionPostRepository postRepository;


    @Override
    public List<PNotification> getNotifs(String userID) {
        List<PNotification> notificationList = notificationRepository.findByUserToIdAndDeliveredFalse(userID);
        notificationList.forEach(x -> x.setDelivered(true));
        notificationRepository.saveAll(notificationList);
        return notificationList;
    }


    @Override
    public Flux<ServerSentEvent<List<PNotification>>> getNotificationsForAdmin(String userID) {

        if (userID != null) {
            return Flux.interval(Duration.ofSeconds(1))
                    .publishOn(Schedulers.boundedElastic())
                    .map(sequence -> ServerSentEvent.<List<PNotification>>builder().id(String.valueOf(sequence))
                            .event("user-list-event").data(getNotifs(userID))
                            .build());
        }

        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<PNotification>>builder()
                .id(String.valueOf(sequence)).event("user-list-event").data(new ArrayList<>()).build());
    }


    public Flux<ServerSentEvent<List<PNotification>>> getNotificationsForAdminMessage(String userID) {



        if (userID != null) {
            return Flux.interval(Duration.ofSeconds(1))
                    .publishOn(Schedulers.boundedElastic())
                    .map(sequence -> ServerSentEvent.<List<PNotification>>builder().id(String.valueOf(sequence))
                            .event("user-list-event").data(getNotifs(userID))
                            .build());
        }

        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<PNotification>>builder()
                .id(String.valueOf(sequence)).event("user-list-event").data(new ArrayList<>()).build());
    }


    @Override
    public PNotification save(PNotification notification) {
        return notificationRepository.save(notification);
    }
}
