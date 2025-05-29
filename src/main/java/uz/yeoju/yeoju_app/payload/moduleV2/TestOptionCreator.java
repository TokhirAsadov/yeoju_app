package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestOptionCreator {
    public String questionId;
    public String text;
    public Integer score;
    public boolean isCorrect;
}
