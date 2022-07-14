package uz.yeoju.yeoju_app.entity.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsLongEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Retake extends AbsLongEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    private String commandNumber;
    private Date commandDate;
    private String year;
    private Integer semester;
    private String score;

    public Retake(Long id, User user, Lesson lesson, String commandNumber, Date commandDate, String year, Integer semester, String score) {
        super(id);
        this.user = user;
        this.lesson = lesson;
        this.commandNumber = commandNumber;
        this.commandDate = commandDate;
        this.year = year;
        this.semester = semester;
        this.score = score;
    }
}
