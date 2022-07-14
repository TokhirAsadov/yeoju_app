package uz.yeoju.yeoju_app.payload.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.LessonDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCreditDto {
    private Long id;
    private LessonDto lessonDto;
    private Integer credit;
    private GroupDto groupDto;
    private String year;
    private Integer semester;
}
