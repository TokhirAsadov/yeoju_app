package uz.yeoju.yeoju_app.payload;

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

}
