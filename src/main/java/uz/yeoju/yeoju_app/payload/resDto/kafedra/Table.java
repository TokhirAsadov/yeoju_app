package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.LessonXml;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Show;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private TeacherData teacherData;
    private List<Show> shows;
}
