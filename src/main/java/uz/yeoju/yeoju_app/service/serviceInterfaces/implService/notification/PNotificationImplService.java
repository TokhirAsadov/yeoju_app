package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notification;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.payload.resDto.notification.NotificationResDto;
import uz.yeoju.yeoju_app.repository.permissionPost.PNotificationRepository;
import uz.yeoju.yeoju_app.repository.permissionPost.PermissionPostRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PNotificationImplService implements PNotificationService{
    private final PNotificationRepository notificationRepository;
    private final PermissionPostRepository postRepository;


    @Override
    public List<PNotification> getNotifs(String userID) {
        List<PNotification> notificationList = notificationRepository.findByUserToIdAndDeliveredFalse(userID);
//        List<String> ids = notificationRepository.getIdsByUserToAndDeliveredFalse(userID);
//        System.out.println("12341234 --------------------------------");
//        System.out.println("11111111111111111111111 --- > "+notificationList);
        notificationList.forEach(x -> {
            x.setDelivered(true);
        });
        notificationRepository.saveAll(notificationList);
        return notificationList;
    }

    @Override
    public List<NotificationResDto> getNotifs2(String userID) {
        List<PNotification> notificationList = notificationRepository.findByUserToIdAndDeliveredFalse(userID);
        List<String> ids = notificationList.stream().map(n->n.getId()).collect(Collectors.toList());;


//        System.out.println("11111111111111111111111 --- > "+notificationList);


        notificationList.forEach(x -> {
            x.setDelivered(true);
        });


        notificationRepository.saveAll(notificationList);

        List<NotificationResDto> list =new ArrayList<>();

        ids.forEach(id -> {
            list.add(notificationRepository.getResDtoById(id));
        });


        System.out.println("<> <> <> done list you can show it <> <> <>");

        return list;
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

    @Override
    public Flux<ServerSentEvent<List<NotificationResDto>>> getNotificationsForAdmin2(String userId) {

        if (userId != null) {
            return Flux.interval(Duration.ofSeconds(1))
                    .publishOn(Schedulers.boundedElastic())
                    .map(sequence -> ServerSentEvent.<List<NotificationResDto>>builder().id(String.valueOf(sequence))
                            .event("user-list-event").data(getNotifs2(userId))
                            .build());
        }

        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> ServerSentEvent.<List<NotificationResDto>>builder()
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
//

    @Override
    public PNotification save(PNotification notification) {
        return notificationRepository.save(notification);
    }
}
