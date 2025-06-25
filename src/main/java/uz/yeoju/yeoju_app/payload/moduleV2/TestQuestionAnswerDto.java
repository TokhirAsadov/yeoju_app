package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionAnswerDto {
    private String questionId;
    private String questionText;
    private String type;
    private List<TestOptionDto> options;
    private String writtenAnswer;
    private Boolean isCorrect;
    private Integer score;
    private Boolean teacherChecked;
}
