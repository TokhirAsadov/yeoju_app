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
    private Float grade;
    public String educationYearId;
    public String lessonId;
    public Set<String> groupsIds;

    public GradeForAttendanceDto(Float grade,String educationYearId, String lessonId, Set<String> groupsIds) {
        this.grade = grade;
        this.educationYearId = educationYearId;
        this.lessonId = lessonId;
        this.groupsIds = groupsIds;
    }
}
