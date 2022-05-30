package uz.yeoju.yeoju_app.entity.workingTableOfWorkers;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.Lesson;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkingDayOfPP extends AbsEntity {

    @ManyToOne
    @NotNull
    private WorkingTimeOfPP workingTimeOfPP;

    @ManyToOne
    private Lesson lesson;

    @ManyToMany
    private Set<Group> groups;

    private Timestamp startedDate;
    private Timestamp finishedDate;

    private String numberOfRoom;
    private boolean active;
}
