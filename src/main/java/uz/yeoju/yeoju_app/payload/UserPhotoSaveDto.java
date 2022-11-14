package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhotoSaveDto {
    private String id;
    private String userId;
    private Attachment attachment;
    private boolean active;
}
