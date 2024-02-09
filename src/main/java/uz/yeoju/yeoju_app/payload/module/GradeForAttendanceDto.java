package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeForAttendanceDto {
    public String id;
    public String educationYearId;
    public String lessonId;
    public Set<String> groupsIds;

    public GradeForAttendanceDto(String educationYearId, String lessonId, Set<String> groupsIds) {
        this.educationYearId = educationYearId;
        this.lessonId = lessonId;
        this.groupsIds = groupsIds;
    }
}
