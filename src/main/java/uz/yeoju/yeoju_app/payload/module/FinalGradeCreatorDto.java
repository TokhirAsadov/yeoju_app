package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalGradeCreatorDto {
    public Double grade;
    public Double extraGrade;
    public String studentId;
    public String vedimostId;

    public FinalGradeCreatorDto(Double grade, String studentId, String vedimostId) {
        this.grade = grade;
        this.studentId = studentId;
        this.vedimostId = vedimostId;
    }
}
