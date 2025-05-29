package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;

import java.util.List;

@Deprecated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionCreator {
    public String courseTestId;
    public String questionText;
    public TestType type;
    public List<String> options;
    public List<String> correctAnswers;
}
