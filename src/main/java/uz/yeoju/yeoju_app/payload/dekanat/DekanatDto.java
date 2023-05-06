package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.OwnerDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DekanatDto {
    private String id;
    private String name;
    private Set<String> facultiesName;
    private Set<String> roles;
    private Set<String> positions;

    private OwnerDto owner;
    private String room;
    private String phone;
    private String eduType;

    public DekanatDto(String id, String name, Set<String> facultiesName, Set<String> roles, Set<String> positions, OwnerDto owner, String room, String phone) {
        this.id = id;
        this.name = name;
        this.facultiesName = facultiesName;
        this.roles = roles;
        this.positions = positions;
        this.owner = owner;
        this.room = room;
        this.phone = phone;
    }

    public DekanatDto(String id, String name, Set<String> facultiesName, Set<String> roles, Set<String> positions) {
        this.id = id;
        this.name = name;
        this.facultiesName = facultiesName;
        this.roles = roles;
        this.positions = positions;
    }

    public DekanatDto(String id, String name, Set<String> facultiesName) {
        this.id = id;
        this.name = name;
        this.facultiesName = facultiesName;
    }
}
