package uz.yeoju.yeoju_app.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnableTeacherControlTest extends AbsEntity {

    @ManyToOne
    private User teacher;

    @ManyToMany
    private Set<Lesson> lessons;

    public EnableTeacherControlTest(String id, User teacher, Set<Lesson> lessons) {
        super(id);
        this.teacher = teacher;
        this.lessons = lessons;
    }
}
