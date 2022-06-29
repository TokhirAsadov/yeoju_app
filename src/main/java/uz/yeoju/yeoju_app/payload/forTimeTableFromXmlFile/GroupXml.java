package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupXml {
    private String id;
    private String name;
    private String classId;
    private List<String> studentIds;
    private String entireClass;
    private String divisionTag;
}
