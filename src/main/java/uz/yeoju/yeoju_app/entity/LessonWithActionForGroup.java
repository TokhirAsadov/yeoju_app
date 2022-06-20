package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LessonWithActionForGroup extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    private Integer credit;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Group> groups;
}
