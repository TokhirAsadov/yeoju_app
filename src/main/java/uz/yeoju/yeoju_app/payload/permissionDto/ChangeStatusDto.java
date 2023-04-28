package uz.yeoju.yeoju_app.payload.permissionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeStatusDto {
    private String id;
    private PPostStatus status;
}
