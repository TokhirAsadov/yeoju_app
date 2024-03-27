package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradesWithThemeDto {
    public String id;
    public Timestamp time;
    public String themeName;
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
