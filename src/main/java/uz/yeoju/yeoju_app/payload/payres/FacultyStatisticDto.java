package uz.yeoju.yeoju_app.payload.payres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyStatisticDto {
    private String name;
    private Integer comeCount;
    private Integer allCount;
}
