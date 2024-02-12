package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeForAttendance extends AbsEntity {
    private Float grade;
    private Integer credit;
    @ManyToOne
    private EducationYear educationYear;
    @ManyToOne
    private Lesson lesson;
    @ManyToMany
    private Set<Group> groups;
}
