package uz.yeoju.yeoju_app.payload.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class KafedraDto {
    private String id;
    private String nameUz;
    private String nameRu;
    private String nameEn;

    public KafedraDto(String nameUz) {
        this.nameUz = nameUz;
    }

    public KafedraDto(String nameUz, String nameRu, String nameEn) {
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }
}
