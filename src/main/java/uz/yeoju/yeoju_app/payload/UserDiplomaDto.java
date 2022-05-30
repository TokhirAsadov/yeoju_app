package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDiplomaDto {
    private String id;
    private UserDto userDto;
    private Set<Attachment> attachmentList;
    private boolean active;
}
