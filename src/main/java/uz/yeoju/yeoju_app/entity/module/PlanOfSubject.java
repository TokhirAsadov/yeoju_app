package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlanOfSubject extends AbsEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    private EducationYear educationYear;
    @ManyToOne
    private Lesson subject;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Group> groups;
    @ManyToOne
    private EducationType educationType;
    @ManyToOne
    private EducationLanguage educationLanguage;
    private Integer level;
}
