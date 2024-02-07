package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAttendanceDto {
    public String id;
    public Integer year;
    public Integer week;
    public Integer weekday;
    public Integer section;
    public Boolean isCome;
    public String studentId;
    public String room;

    public DynamicAttendanceDto(Integer year, Integer week, Integer weekday, Integer section, Boolean isCome, String studentId, String room) {
        this.year = year;
        this.week = week;
        this.weekday = weekday;
        this.section = section;
        this.isCome = isCome;
        this.studentId = studentId;
        this.room = room;
    }
}
