package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    public String id;
    public String title;
    private List<ModuleProgressResponse> modules;

    public CourseResponse(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
