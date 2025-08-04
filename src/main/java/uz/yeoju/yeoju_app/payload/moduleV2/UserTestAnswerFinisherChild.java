package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestAnswerFinisherChild {
    public String testQuestionId;
    public String writtenAnswer;
    public List<String> selectedOptionsIds;
}

