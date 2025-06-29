package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestAnswerV2Finisher {
    public String testId;
    public String userId;
    public List<UserTestAnswerV2FinisherChild> answers;
}

