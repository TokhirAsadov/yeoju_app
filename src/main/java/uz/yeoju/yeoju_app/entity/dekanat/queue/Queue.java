package uz.yeoju.yeoju_app.entity.dekanat.queue;

import lombok.*;
import org.springframework.data.annotation.Id;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Queue extends AbsEntity {

    private String number;

    @Builder.Default
    private QueueStatusEnum status = QueueStatusEnum.RUNNABLE;

    private Timestamp startedAt;

    private Timestamp calledAt;

    private Timestamp waitingAt;

    private Timestamp finishedAt;

}
