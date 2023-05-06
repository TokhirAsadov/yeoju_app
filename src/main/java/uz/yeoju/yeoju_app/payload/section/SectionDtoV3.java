package uz.yeoju.yeoju_app.payload.section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.OwnerDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDtoV3 {

    private String id;
    private String name;
    private Set<String> roles;
    private Set<String> positions;
    private OwnerDto owner;
    private String room;
    private String phone;

    public SectionDtoV3(String id, String name, Set<String> roles, Set<String> positions) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.positions = positions;
    }
}
