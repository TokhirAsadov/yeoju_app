package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CTestCreator {
    public String title;
    public String courseId;
    public String moduleId;
    @Min(value = 50, message = "Minimum 50% bo'lishi kerak")
    @Max(value = 100, message = "Minimum 100% bo'lishi kerak")
    public Double passingPercentage;
}
