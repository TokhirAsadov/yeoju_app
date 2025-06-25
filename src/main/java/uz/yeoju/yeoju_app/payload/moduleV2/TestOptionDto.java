package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestOptionDto {
    private String id;
    private String text;
    private Boolean correct;
    private Integer score;
    private Boolean selectedByUser; // ✅ user tanlaganmi yoki yo‘q
}
