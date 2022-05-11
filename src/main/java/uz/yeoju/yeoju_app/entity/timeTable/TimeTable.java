package uz.yeoju.yeoju_app.entity.timeTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.enums.DaysOfWeek;
import uz.yeoju.yeoju_app.entity.enums.TimeTableStatus;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TimeTable extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private TimeTableStatus status;

    @Enumerated(EnumType.STRING)
    private DaysOfWeek daysOfWeek;

    @OneToMany
    private Set<Group> group;
}
