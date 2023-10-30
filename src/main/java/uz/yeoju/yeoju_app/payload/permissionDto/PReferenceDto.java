package uz.yeoju.yeoju_app.payload.permissionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.permissionPost.TypeOfReference;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PReferenceDto {
    private String id;
    private Long queue;
    private Long numeration;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;

    private PPostStatus status;
    private UserDto2 user;
    private TypeOfReference type;
    private String description;
    private String educationYear;
}
