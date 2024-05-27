package uz.yeoju.yeoju_app.entity.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.EducationLanguage;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachersHoursOfSubject extends AbsEntity {
    @ManyToOne
    private User teacher;
    @ManyToOne
    private EducationYear educationYear;
    private Integer hour;

    @OneToMany
    private List<Lesson> subjects;
    private EducationLanguage educationLanguage;
}
