package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestAnswerCreator {
    public String userId;
    public String testQuestionId;
    public List<String> selectedOptions;
    public String writtenAnswer; // WRITTEN uchun
}

