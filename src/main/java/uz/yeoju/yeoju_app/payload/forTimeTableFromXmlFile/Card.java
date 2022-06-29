package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String lessonId;
    private List<String> classroomIds;
    private Integer period;
    private List<String> weeks;
    private List<String> terms;
    private List<String> days;
}
