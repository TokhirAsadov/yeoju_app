package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseProgressResponse {
    public String studentId;
    public String studentFullName;
    public String progress;
    public double progressPercent;
    public List<ModuleProgressResponse> modules;
    public boolean canStartTest;
    public Map<String, Object> testInfo;
}
