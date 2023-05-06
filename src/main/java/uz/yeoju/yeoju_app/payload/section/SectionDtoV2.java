package uz.yeoju.yeoju_app.payload.section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDtoV2 {

    private String id;
    private String name;
    private Set<String> roles;
    private Set<String> positions;
    private String ownerId;
    private String room;
    private String phone;

    public SectionDtoV2(String id, String name, Set<String> roles, Set<String> positions) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.positions = positions;
    }
}
