package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObyektivkaDto {
    private String id;
    private UserDto userDto;
    private Attachment attachment;
    private boolean active;
}
