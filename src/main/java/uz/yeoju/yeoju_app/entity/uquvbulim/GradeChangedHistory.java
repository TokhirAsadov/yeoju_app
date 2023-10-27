package uz.yeoju.yeoju_app.entity.uquvbulim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.module.GradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeChangedHistory extends AbsEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private GradeOfStudentByTeacher grade;

    private Integer oldGrade;
    private Integer newGrade;

    public GradeChangedHistory(String id, GradeOfStudentByTeacher grade, Integer oldGrade, Integer newGrade) {
        super(id);
        this.grade = grade;
        this.oldGrade = oldGrade;
        this.newGrade = newGrade;
    }
}
