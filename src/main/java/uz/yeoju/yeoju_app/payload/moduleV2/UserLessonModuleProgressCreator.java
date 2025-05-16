package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLessonModuleProgressCreator {
    public String userId;
    public String lessonModuleId;
    public boolean completed;
}
