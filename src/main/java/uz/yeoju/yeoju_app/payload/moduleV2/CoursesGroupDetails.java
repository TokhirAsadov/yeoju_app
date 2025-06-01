package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.Data;

import java.util.List;

@Data
public class CoursesGroupDetails {
    private List<CourseDto> courses;
    private JsonObject educationType;
    private JsonObject educationLanguage;
//    @JsonIgnore
//    private Course courseObject; // bu keyin parse qilinadi
}
