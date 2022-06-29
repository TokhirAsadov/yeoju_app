package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonXml {
    private String id;
    private List<String> classIds;
    private String subjectId;
    private String periodsPerCard;
    private String periodsPerWeek;
    private List<String> teacherIds;
    private List<String> groupIds;
    private String seminarGroup;
    private String termsDefId;
    private String weeksDefId;
    private String daysDefId;
    private String capacity;
    private String partnerId;
}
