package uz.yeoju.yeoju_app.payload.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.OwnerDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafedraV3Dto {
    private String id;
    private String nameUz;
    private String nameRu;
    private String nameEn;

    private Set<String> roles;
    private Set<String> positions;

    private OwnerDto owner;
    private String room;
    private String phone;

}
