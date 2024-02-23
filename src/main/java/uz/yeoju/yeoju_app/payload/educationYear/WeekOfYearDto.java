package uz.yeoju.yeoju_app.payload.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekOfYearDto {
    private String id;
    private Date start;
    private Date end;
    private String type;
    private String eduType;
    private Integer year;
    private Integer sortNumber;
    private Integer weekNumber;
    private String educationYearId;
}
