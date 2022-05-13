package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLanguageDto {
    private String id;
    private String name;

    public EducationLanguageDto(String name) {
        this.name = name;
    }
}
