package uz.yeoju.yeoju_app.payload.dekanat.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.yeoju.yeoju_app.entity.dekanat.queue.QueueStatusEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QueueStatusChangerDto {
    public String id;
    public QueueStatusEnum statusEnum;
}
