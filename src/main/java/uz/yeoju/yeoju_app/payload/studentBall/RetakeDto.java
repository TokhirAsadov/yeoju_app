package uz.yeoju.yeoju_app.payload.studentBall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.payload.UserDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetakeDto {
    private Long id;
    private UserDto userDto;
    private LessonDto lessonDto;
    private String commandNumber;
    private Date commandDate;
    private String year;
    private Integer semester;
    private String score;

    public RetakeDto(UserDto userDto, LessonDto lessonDto, String commandNumber, Date commandDate, String year, Integer semester, String score) {
        this.userDto = userDto;
        this.lessonDto = lessonDto;
        this.commandNumber = commandNumber;
        this.commandDate = commandDate;
        this.year = year;
        this.semester = semester;
        this.score = score;
    }
}
