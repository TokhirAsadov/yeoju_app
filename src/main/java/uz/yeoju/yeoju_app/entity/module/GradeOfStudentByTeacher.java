package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeOfStudentByTeacher extends AbsEntity {

    @ManyToOne
    private GradeOfStudentByTeacher failGrade;
//    @Size(max=6, message = "Name should have at least two characters")
    @DecimalMax(value = "6",message = "Maximum grade can be 6.")
    @DecimalMin(value = "0",message = "Minimum grade can be 0.")
    private Float grade;
    private Timestamp time;
    private String description;

    @ManyToOne
    private User student;
    @ManyToOne
    private ThemeOfSubjectForGradeByTeacher theme;
    @ManyToOne
    private Lesson lesson;
    @ManyToOne
    private EducationYear educationYear;

}
