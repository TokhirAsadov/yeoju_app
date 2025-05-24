package uz.yeoju.yeoju_app.payload.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleProgressResponse {
    private String moduleId;
    private String title;
    private boolean completed;
    private String done;
    private String theme;
}
