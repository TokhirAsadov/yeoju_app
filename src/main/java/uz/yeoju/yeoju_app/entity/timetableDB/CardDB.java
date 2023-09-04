package uz.yeoju.yeoju_app.entity.timetableDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CardDB extends AbsEntity {

    @ManyToOne
    private LessonDB lesson;
    private String classroom;
    private Integer period;
    private String betweenDuringDate;
    private Integer day;
    private String dayName;
    @ManyToOne
    private WeekOfEducationYear weekOfEducationYear;

}
