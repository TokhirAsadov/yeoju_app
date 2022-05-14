package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationTypeDto {
    private String id;
    private String name;

    public EducationTypeDto(String name) {
        this.name = name;
    }
}
