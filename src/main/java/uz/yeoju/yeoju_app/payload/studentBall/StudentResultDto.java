package uz.yeoju.yeoju_app.payload.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResultDto {
    private Long id;
    private UserDto userDto;
    private SubjectCreditDto subjectCreditDto;
    private String score;
}
