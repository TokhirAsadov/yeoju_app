package uz.yeoju.yeoju_app.entity.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentResult extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubjectCredit subjectCredit;

    private String score;

    public StudentResult(Long id, User user, SubjectCredit subjectCredit, String score) {
        super(id);
        this.user = user;
        this.subjectCredit = subjectCredit;
        this.score = score;
    }
}
