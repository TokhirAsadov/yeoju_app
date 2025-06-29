package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.Data;
import uz.yeoju.yeoju_app.payload.moduleV2.JsonObject;


@Data
public class CourseGroupDetailsV2 {
    private CourseV2Dto course;
    private JsonObject educationType;
    private JsonObject educationLanguage;
}
