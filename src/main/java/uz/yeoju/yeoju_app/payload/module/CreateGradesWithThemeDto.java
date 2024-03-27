package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradesWithThemeDto {
    public String id;
    public Timestamp time;
    public String themeName;
    @DecimalMax(value = "6",message = "Maximum grade can be 6.")
    @DecimalMin(value = "0",message = "Minimum grade can be 0.")
    public Double maxGrade;
    public String subjectId;
    public String groupId;
    public String educationId;
    public Set<ChildOfCreateGradesWithThemeDto> grades;

    public CreateGradesWithThemeDto(Timestamp time, String themeName, String subjectId, String groupId, String educationId, Set<ChildOfCreateGradesWithThemeDto> grades) {
        this.time = time;
        this.themeName = themeName;
        this.subjectId = subjectId;
        this.groupId = groupId;
        this.educationId = educationId;
        this.grades = grades;
    }
}
