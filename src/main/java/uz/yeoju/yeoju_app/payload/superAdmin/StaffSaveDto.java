package uz.yeoju.yeoju_app.payload.superAdmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffSaveDto {
    private String userId;
    private String sectionId;
    private String roleId;
}
