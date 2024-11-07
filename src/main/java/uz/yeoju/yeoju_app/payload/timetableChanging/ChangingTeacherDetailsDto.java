package uz.yeoju.yeoju_app.payload.timetableChanging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChangingTeacherDetailsDto {
    public String id;
    public String shortName;
    public Integer year;
    public Integer week;
    public WeekType type;
}
