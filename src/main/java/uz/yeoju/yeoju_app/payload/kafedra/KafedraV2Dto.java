package uz.yeoju.yeoju_app.payload.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafedraV2Dto {
    private String id;
    private String nameUz;
    private String nameRu;
    private String nameEn;

    private Set<String> roles;
    private Set<String> positions;

    private String ownerId;
    private String room;
    private String phone;

    public KafedraV2Dto(String nameUz) {
        this.nameUz = nameUz;
    }

    public KafedraV2Dto(String nameUz, String nameRu, String nameEn) {
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    public KafedraV2Dto(String nameUz, String nameRu, String nameEn, Set<String> roles, Set<String> positions) {
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.roles = roles;
        this.positions = positions;
    }

    public KafedraV2Dto(String id, String nameUz, String nameRu, String nameEn, Set<String> roles, Set<String> positions) {
        this.id = id;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.roles = roles;
        this.positions = positions;
    }
}
