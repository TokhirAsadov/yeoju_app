package uz.yeoju.yeoju_app.payload.moduleV2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleResponse {
    private String id;
    private String title;
    private String theme;
    @JsonProperty("topic_files")
    private List<TopicFileOfLineV2Dto> topicFiles;
}
