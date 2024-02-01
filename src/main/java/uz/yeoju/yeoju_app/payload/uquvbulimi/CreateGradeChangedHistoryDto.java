package uz.yeoju.yeoju_app.payload.uquvbulimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradeChangedHistoryDto {
    private String gradeId;
    private Float oldGrade;
    private Float newGrade;
}
