package uz.yeoju.yeoju_app.payload.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsLessonCount {
    private Integer week;
    private String groupName;
    private List<Integer> count;
}
