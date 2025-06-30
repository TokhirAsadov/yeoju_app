package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestOptionV2Creator {
    public String text;
    public boolean isCorrect=false;
}
