package uz.yeoju.yeoju_app.entity.timeTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.ProfissorPedagog;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DayLessonOfTimeTable extends AbsEntity {

    @ManyToOne
    private TimeTable timeTable;

    @ManyToOne
    private Lesson lesson;

    @ManyToMany
    private Set<ProfissorPedagog> profissorPedagogs;

    private Timestamp startedDate;
    private Timestamp finishedDate;

    private String numberOfRoom;
    private boolean active;
}
