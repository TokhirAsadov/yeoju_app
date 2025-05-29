package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestOptionCreatorV2 {
    public String text;
    public Integer score;
    public boolean isCorrect=false;
}
