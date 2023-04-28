package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private List<Teacher> teachers;
    private List<String> groups;
    private String kafedraId;
}
