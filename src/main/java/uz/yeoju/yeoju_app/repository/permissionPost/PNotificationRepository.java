package uz.yeoju.yeoju_app.repository.permissionPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.payload.resDto.notification.NotificationResDto;

import java.util.List;

public interface PNotificationRepository extends JpaRepository<PNotification,String> {
    List<PNotification> findByUserToIdAndDeliveredFalse(String userTo_id);

    @Query(value = "select id from PNotification\n" +
            "where userTo_id=?1 and delivered=0 order by createdAt desc",nativeQuery = true)
    List<String> getIdsByUserToAndDeliveredFalse(String userTo_id);

    @Query(value = "select id,content,type,createdAt,createdBy,updatedAt,updatedBy,userTo_id as userToId,userFrom_id as userFromId from PNotification where id=?1 ",nativeQuery = true)
    NotificationResDto getResDtoById(String id);
}
