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
    public String subjectId;
    public String groupId;
    public String educationYearId;
    public Set<ChildOfCreateGradesWithThemeDto> grades;
}
