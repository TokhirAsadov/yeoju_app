package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadCounterV2 extends AbsEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicFileOfLineV2 topic;
    private Integer count=0;
}
