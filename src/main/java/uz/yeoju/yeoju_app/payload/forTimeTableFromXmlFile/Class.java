package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    private String id;
    private String name;
    private String shortName;
    private String teacherId;
    private List<String> classRoomIds;
    private String grade;
    private String partnerId;
}
