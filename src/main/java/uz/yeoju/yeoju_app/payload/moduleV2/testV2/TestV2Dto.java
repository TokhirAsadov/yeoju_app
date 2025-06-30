package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.Data;

@Data
public class TestV2Dto {
    private String id;
    private String title;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private Integer singleChoiceCount;
    private Integer singleChoiceBall;
    private Integer multipleChoiceCount;
    private Integer multipleChoiceBall;
    private Integer writtenCount;
    private Integer writtenBall;
    private Integer passingBall;
}

