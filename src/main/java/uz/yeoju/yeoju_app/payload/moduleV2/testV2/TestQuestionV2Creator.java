package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionV2Creator {
    public String courseId;
    public String moduleId;
    public String questionText;
    public TestType type;
    public List<TestOptionV2Creator> options;
}
