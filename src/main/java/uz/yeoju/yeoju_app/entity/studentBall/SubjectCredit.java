package uz.yeoju.yeoju_app.entity.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.temp.AbsLongEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubjectCredit extends AbsLongEntity {

    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    private String credit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;
    private String year;
    private String semester;

    public SubjectCredit(Long id, Lesson lesson, String credit, Group group, String year, String semester) {
        super(id);
        this.lesson = lesson;
        this.credit = credit;
        this.group = group;
        this.year = year;
        this.semester = semester;
    }
}
