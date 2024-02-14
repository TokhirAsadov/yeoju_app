package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiDynamicAttendanceDto {
    public String id;
    public Integer year;
    public Integer week;
    public Integer weekday;
    public Integer section;
    public Boolean isCome;
    public Set<String> studentsIds;
    public String room;

    public MultiDynamicAttendanceDto(Integer year, Integer week, Integer weekday, Integer section, Boolean isCome, Set<String> studentsIds, String room) {
        this.year = year;
        this.week = week;
        this.weekday = weekday;
        this.section = section;
        this.isCome = isCome;
        this.studentsIds = studentsIds;
        this.room = room;
    }
}
