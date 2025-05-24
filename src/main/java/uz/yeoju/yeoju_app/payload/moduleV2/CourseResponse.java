package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    public String id;
    private String title;
    private double progress;
    private String done;
    private List<ModuleProgressResponse> modules;
    private boolean canStartTest;
    private Map<String, Object> testResultInfo; // ✅ Qo‘shildi

    public CourseResponse(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
