package uz.yeoju.yeoju_app.entity.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WeekOfEducationYear extends AbsEntity {
    private Date start;
    private Date end;

    @Enumerated(EnumType.STRING)
    private WeekType type;
    private Integer sortNumber;
    private Integer weekNumber;

    @Enumerated(EnumType.STRING)
    private WeekEduType eduType;

    private Integer course;
    private Integer year;

}
