package uz.yeoju.yeoju_app.payload.staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffSaveFromSection {
    private String id;
    private String userId;
    private String sectionId;
    private Long positionId;

    public StaffSaveFromSection(String userId, String sectionId, Long positionId) {
        this.userId = userId;
        this.sectionId = sectionId;
        this.positionId = positionId;
    }
}
