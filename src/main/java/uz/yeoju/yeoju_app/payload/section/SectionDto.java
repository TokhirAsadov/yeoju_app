package uz.yeoju.yeoju_app.payload.section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class SectionDto {
    private String id;
    private String name;

    public SectionDto(String name) {
        this.name = name;
    }
}
