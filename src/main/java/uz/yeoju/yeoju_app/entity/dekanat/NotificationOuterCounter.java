package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationOuterCounter extends AbsEntity {

    private Long queue;

    @ManyToOne
    private User user;

    @ManyToOne
    private NotificationOuter notificationOuter;

    public NotificationOuterCounter(String id, Long queue, User user, NotificationOuter notificationOuter) {
        super(id);
        this.queue = queue;
        this.user = user;
        this.notificationOuter = notificationOuter;
    }
}
