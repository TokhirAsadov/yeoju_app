package uz.yeoju.yeoju_app.payload.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonData {
    private Date start;
    private String lessonName;
    private Integer section;
    private Integer weekDay;
    private Integer sortWeek;
}
