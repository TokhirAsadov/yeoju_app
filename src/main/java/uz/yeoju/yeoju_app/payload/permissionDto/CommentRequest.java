package uz.yeoju.yeoju_app.payload.permissionDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String permissionPostId;
    private String commit;
}
