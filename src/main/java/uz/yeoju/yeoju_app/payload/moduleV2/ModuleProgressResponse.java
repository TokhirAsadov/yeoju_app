package uz.yeoju.yeoju_app.payload.moduleV2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleProgressResponse {
    private String moduleId;
    private String title;
    private boolean completed;
    private String done;
    private String theme;
    @JsonProperty("topic_files")
    private List<TopicFileOfLineV2Dto> topicFiles;

    @JsonProperty("module_test")
    private ModuleTestDto moduleTest;

    @JsonProperty("test_result_info")
    private Map<String, Object> testResultInfo; // ✅ test natijalari uchun

    public ModuleProgressResponse(String moduleId, String title, boolean completed, String done, String theme) {
        this.moduleId = moduleId;
        this.title = title;
        this.completed = completed;
        this.done = done;
        this.theme = theme;
    }
}
