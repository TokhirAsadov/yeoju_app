package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalGradeCreatorChildDto {
    public Double grade;
    public Double extraGrade;
    public String studentId;
}
