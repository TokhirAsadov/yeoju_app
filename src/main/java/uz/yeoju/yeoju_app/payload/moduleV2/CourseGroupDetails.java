package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.Data;

import java.util.List;

@Data
public class CourseGroupDetails {
    private CourseDto course;
    private List<GroupPlanV2DTO> groups;
//    @JsonIgnore
//    private Course courseObject; // bu keyin parse qilinadi
}
