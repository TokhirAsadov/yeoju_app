package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {
    private Integer dayNumber;
    private Integer hourNumber;
    private String room;
    private String periodStartAndEndTime;
    private String daysName;
    private String lessonName;
    private List<String> teacherName;
}
