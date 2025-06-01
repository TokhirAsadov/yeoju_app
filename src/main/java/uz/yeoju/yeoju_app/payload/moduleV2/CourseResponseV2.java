package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseV2 {
    public String id;
    private String title;
    private List<JsonObject> faculties;

    public CourseResponseV2(String id, String title) {
        this.id = id;
        this.title = title;
    }

}
