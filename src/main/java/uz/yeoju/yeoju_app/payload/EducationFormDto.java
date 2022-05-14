package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationFormDto {
    private String id;
    private String name;

    public EducationFormDto(String name) {
        this.name = name;
    }
}
