package uz.yeoju.yeoju_app.entity.timetableDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PeriodDB extends AbsEntity {
    private Integer name;
    private Integer shortName;
    private Integer period;
    private String startTime;
    private String endTime;

    public PeriodDB(String id, Integer name, Integer shortName, Integer period, String startTime, String endTime) {
        super(id);
        this.name = name;
        this.shortName = shortName;
        this.period = period;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
