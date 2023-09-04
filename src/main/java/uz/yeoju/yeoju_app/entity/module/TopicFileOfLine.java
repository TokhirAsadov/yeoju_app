package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicFileOfLine extends AbsEntity {
    private String name;
    private String fileType;
    private String contentType;

    @Enumerated(EnumType.STRING)
    private TopicFileType type;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<LineOfPlan> lineOfPlans;
    private String url;
}
