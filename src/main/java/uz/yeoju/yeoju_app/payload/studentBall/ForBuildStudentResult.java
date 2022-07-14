package uz.yeoju.yeoju_app.payload.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForBuildStudentResult {
    private String studentId;
    private String year;
    private Integer semester;
    private String subjectName;
    private Integer credit;
    private Integer score;
}
