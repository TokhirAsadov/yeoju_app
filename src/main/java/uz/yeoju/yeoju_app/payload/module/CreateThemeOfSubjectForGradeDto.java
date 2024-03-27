package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateThemeOfSubjectForGradeDto {
    @Size(min = 3,message = "Mazvu nomining uzunligi kamida 4 bo`lishi kerak.")
    public String name;
    @DecimalMax(value = "6",message = "Maximum grade can be 6.")
    @DecimalMin(value = "0",message = "Minimum grade can be 0.")
    public Double maxGrade;
    public String groupId;
    public String subjectId;
    public String educationYearId;
}
