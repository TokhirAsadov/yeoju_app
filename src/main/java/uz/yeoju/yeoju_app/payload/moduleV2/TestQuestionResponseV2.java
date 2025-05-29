package uz.yeoju.yeoju_app.payload.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionResponseV2 {
    public String id;
    public TestType type;
    @JsonIgnore
    public String courseTestId;
    public String questionText;
    public List<TestOptionResponseV2> options;
}
