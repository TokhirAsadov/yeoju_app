package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonModuleResponse {
    private String id;
    private String title;
    private String theme;
    private boolean isRead;

    public LessonModuleResponse(String id, String title, String theme) {
        this.id = id;
        this.title = title;
        this.theme = theme;
    }
}
