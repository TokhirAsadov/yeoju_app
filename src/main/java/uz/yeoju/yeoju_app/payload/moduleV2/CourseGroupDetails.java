package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.Data;


@Data
public class CourseGroupDetails {
    private CourseDto course;
    private JsonObject educationType;
    private JsonObject educationLanguage;
}
