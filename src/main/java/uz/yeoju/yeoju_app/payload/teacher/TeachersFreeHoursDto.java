package uz.yeoju.yeoju_app.payload.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachersFreeHoursDto {
    public String id;
    public String educationYearId;
    public String day;
    public String schedule;

    public TeachersFreeHoursDto(String educationYearId, String day, String schedule) {
        this.educationYearId = educationYearId;
        this.day = day;
        this.schedule = schedule;
    }
}
