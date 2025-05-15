package uz.yeoju.yeoju_app.entity.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlanOfSubjectV2 extends AbsEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private EducationYear educationYear;

    @ManyToOne
    private Lesson subject;

    @ManyToOne
    private EducationType educationType;

    @ManyToOne
    private EducationLanguage educationLanguage;

    private Integer level;
}
