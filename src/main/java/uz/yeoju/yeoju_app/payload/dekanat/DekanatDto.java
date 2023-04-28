package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public DekanatDto(String id, String name, Set<String> facultiesName) {
        this.id = id;
        this.name = name;
        this.facultiesName = facultiesName;
    }
}
