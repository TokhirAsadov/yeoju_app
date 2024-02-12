package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeForAttendanceDto {
    public String id;
    private Float grade;
    @NotNull()
    private Integer credit;
    public String educationYearId;
    public String lessonId;
    public Set<String> groupsIds;

    public GradeForAttendanceDto(Float grade,Integer credit, String educationYearId, String lessonId, Set<String> groupsIds) {
        this.credit = credit;
        this.grade = grade;
        this.educationYearId = educationYearId;
        this.lessonId = lessonId;
        this.groupsIds = groupsIds;
    }
}
