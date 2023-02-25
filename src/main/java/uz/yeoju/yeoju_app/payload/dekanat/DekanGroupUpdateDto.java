package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DekanGroupUpdateDto {
    private String id;
    private String name;
    private Integer level;
    private String language;
    private String type;
    private String form;
    private String faculty;

    public DekanGroupUpdateDto(String name, Integer level, String language, String type, String form, String faculty) {
        this.name = name;
        this.level = level;
        this.language = language;
        this.type = type;
        this.form = form;
        this.faculty = faculty;
    }
}
