package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildOfCreateGradesWithThemeDto {
    public String studentId;

    @DecimalMax(value = "6",message = "Maximum grade can be 6.")
    @DecimalMin(value = "0",message = "Minimum grade can be 0.")
    public Float grade;
}
