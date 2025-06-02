package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    public PlanOfSubjectV2(User user, EducationYear educationYear, Lesson subject,  EducationType educationType, EducationLanguage educationLanguage, Integer level) {
        this.user = user;
        this.educationYear = educationYear;
        this.subject = subject;
        this.educationType = educationType;
        this.educationLanguage = educationLanguage;
        this.level = level;
    }
}
