package uz.yeoju.yeoju_app.entity.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.temp.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubjectCredit extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;
    private Integer credit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
    private String year;
    private Integer semester;

    public SubjectCredit(Long id, Lesson lesson, Integer credit, Group group, String year, Integer semester) {
        super(id);
        this.lesson = lesson;
        this.credit = credit;
        this.group = group;
        this.year = year;
        this.semester = semester;
    }
}
