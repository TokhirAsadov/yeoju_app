package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateThemeOfSubjectForGradeDto {
    public String id;
    @Size(min = 3,message = "Mazvu nomining uzunligi kamida 4 bo`lishi kerak.")
    public String name;
    public String subjectId;
    public String educationYearId;
}
