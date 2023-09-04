package uz.yeoju.yeoju_app.payload.permissionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.permissionPost.TypeOfReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReferenceDto {
    private String studentId;
    private String deanId;
    private String educationYearId;
    private String description;
    private TypeOfReference type;
}
