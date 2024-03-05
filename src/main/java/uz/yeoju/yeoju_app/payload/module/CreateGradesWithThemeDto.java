package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradesWithThemeDto {
    public Timestamp time;
    public String themeName;
    public String subjectId;
    public String educationYearId;
    public Map<String, String> attributes;
}
