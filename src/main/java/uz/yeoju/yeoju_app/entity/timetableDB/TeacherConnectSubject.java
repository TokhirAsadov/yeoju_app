package uz.yeoju.yeoju_app.entity.timetableDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeacherConnectSubject extends AbsEntity {

    @ManyToOne
    private User user; // teacher

    @ManyToOne
    private Lesson lesson; // lesson

    @ManyToMany
    private Set<Group> groups; // groups

    @ManyToOne
    private WeekOfEducationYear weeks; // groups
}
