package uz.yeoju.yeoju_app.entity.dekanat.queue;

import lombok.*;
import org.springframework.data.annotation.Id;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Queue extends AbsEntity {

    private Long number;

    @Enumerated(EnumType.STRING)
    private QueueStatusEnum status = QueueStatusEnum.RUNNABLE;

    private Timestamp startedAt;

    private Timestamp calledAt;

    private Timestamp waitingAt;

    private Timestamp finishedAt;

    public Queue(Long number) {
        this.number = number;
    }
}
