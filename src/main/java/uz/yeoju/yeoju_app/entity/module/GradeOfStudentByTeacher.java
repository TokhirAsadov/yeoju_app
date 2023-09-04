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
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeOfStudentByTeacher extends AbsEntity {

    private Integer grade;
    private Timestamp time;
    private String description;

    @ManyToOne
    private User student;
    @ManyToOne
    private Lesson lesson;
    @ManyToOne
    private EducationYear educationYear;

}
