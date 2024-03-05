package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradesWithThemeDto {
    public String themeName;
    public String subjectId;
    public String educationYearId;
    public Map<String, String> attributes;
}
