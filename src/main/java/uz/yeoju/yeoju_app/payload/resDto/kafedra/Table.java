package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Show;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private TeacherData teacherData;
    private List<Show> shows;
    private int attended; // New field for attended lessons count
    private int notAttended; // New field for not attended lessons count

    public Table(TeacherData teacherData, List<Show> shows) {
        this.teacherData = teacherData;
        this.shows = shows;
    }
}
