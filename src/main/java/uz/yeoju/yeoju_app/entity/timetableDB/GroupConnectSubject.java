package uz.yeoju.yeoju_app.entity.timetableDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GroupConnectSubject extends AbsEntity {

    @ManyToOne
    private Group group;
    @ManyToMany
    private Set<Lesson> lessons;
    @ManyToOne
    private EducationYear educationYear;

}
