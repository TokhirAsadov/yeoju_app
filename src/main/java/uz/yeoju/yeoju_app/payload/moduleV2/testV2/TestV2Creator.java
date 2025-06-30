package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestV2Creator {
    @NotBlank(message = "Test title cannot be empty")
    public String title;
    public Integer singleChoiceCount = 0; // Single choice questions count
    public Integer singleChoiceBall = 0; // har bir single choice dagi tug`ri javob uchun ball
    public Integer multipleChoiceCount = 0; // Multiple choice questions count
    public Integer multipleChoiceBall = 0; // har bir multiple choice dagi tug`ri javob uchun ball
    public Integer writtenCount = 0; // Written questions count
    public Integer writtenBall = 0; // har bir written question uchun ball
    public Integer passingBall = 60;
    public String moduleId;
    public String courseId;

    public TestV2Creator(String title, Integer singleChoiceCount, Integer singleChoiceBall, Integer multipleChoiceCount, Integer multipleChoiceBall, Integer writtenCount, Integer writtenBall, Integer passingBall, String moduleId) {
        this.title = title;
        this.singleChoiceCount = singleChoiceCount;
        this.singleChoiceBall = singleChoiceBall;
        this.multipleChoiceCount = multipleChoiceCount;
        this.multipleChoiceBall = multipleChoiceBall;
        this.writtenCount = writtenCount;
        this.writtenBall = writtenBall;
        this.passingBall = passingBall;
        this.moduleId = moduleId;
    }
}
