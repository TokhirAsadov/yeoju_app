package uz.yeoju.yeoju_app.entity.workingTableOfWorkers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.DaysOfWeek;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.entity.enums.TimeTableStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkingTimeOfPP extends AbsEntity {
    @Enumerated(EnumType.STRING)
    private TimeTableStatus status;

    @Enumerated(EnumType.STRING)
    private DaysOfWeek daysOfWeek;
}
