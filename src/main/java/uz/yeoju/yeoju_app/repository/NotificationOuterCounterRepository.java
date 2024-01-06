package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.NotificationOuterCounter;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentNotificationOuters;

import java.util.Set;

public interface NotificationOuterCounterRepository extends JpaRepository<NotificationOuterCounter,String> {

    @Query(value = "select max(queue) from NotificationOuterCounter",nativeQuery = true)
    Long maxQueue();

    Boolean existsByUserIdAndNotificationOuterId(String user_id, String notificationOuter_id);


}
