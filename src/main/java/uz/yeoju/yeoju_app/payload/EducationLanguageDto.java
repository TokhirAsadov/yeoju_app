package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationLanguageDto {
    private UUID id;
    private String name;

    public EducationLanguageDto(String name) {
        this.name = name;
    }
}
