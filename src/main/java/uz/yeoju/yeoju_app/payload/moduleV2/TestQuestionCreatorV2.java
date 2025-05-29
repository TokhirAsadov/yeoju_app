package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionCreatorV2 {
    public String courseTestId;
    public String questionText;
    public TestType type;
    public List<TestOptionCreatorV2> options;
}
