package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseTestDto {
    private String id;
    private String title;
    private String createdAt;
    private String updatedAt;
    private Double passingPercentage;
    private int question_count;
}
