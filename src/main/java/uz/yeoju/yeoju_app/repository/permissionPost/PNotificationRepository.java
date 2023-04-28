package uz.yeoju.yeoju_app.repository.permissionPost;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;

import java.util.List;

public interface PNotificationRepository extends JpaRepository<PNotification,String> {
    List<PNotification> findByUserToIdAndDeliveredFalse(String userTo_id);
}
