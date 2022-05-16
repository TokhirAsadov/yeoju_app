package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafedraDto {
    private String id;
    private String name;

    public KafedraDto(String name) {
        this.name = name;
    }
}
