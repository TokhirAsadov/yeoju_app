package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vedimost extends AbsEntity {
    private Integer level;
    private Boolean isClosed;
    private Timestamp deadline;
    private Timestamp timeClose;
    @ManyToOne
    private User teacher;
    @ManyToOne
    private Lesson lesson;
    @ManyToOne
    private EducationYear educationYear;
    @ManyToOne
    private Group group;
}