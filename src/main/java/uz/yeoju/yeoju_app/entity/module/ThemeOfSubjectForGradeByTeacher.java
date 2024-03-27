package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeOfSubjectForGradeByTeacher extends AbsEntity {


    @Size(min = 3,message = "Mazvu nomining uzunligi kamida 4 bo`lishi kerak.")
    private String name;
    @DecimalMax(value = "6",message = "Maximum grade can be 6.")
    @DecimalMin(value = "0",message = "Minimum grade can be 0.")
    private Double maxGrade;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.EAGER)
    private EducationYear educationYear;

    public ThemeOfSubjectForGradeByTeacher(String id, String name, Lesson lesson, EducationYear educationYear) {
        super(id);
        this.name = name;
        this.lesson = lesson;
        this.educationYear = educationYear;
    }

    public ThemeOfSubjectForGradeByTeacher(String id, String name, Group group,Lesson lesson, EducationYear educationYear) {
        super(id);
        this.name = name;
        this.group = group;
        this.lesson = lesson;
        this.educationYear = educationYear;
    }
}
