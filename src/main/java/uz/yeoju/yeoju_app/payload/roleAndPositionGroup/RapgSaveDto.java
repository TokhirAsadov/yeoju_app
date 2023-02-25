package uz.yeoju.yeoju_app.payload.roleAndPositionGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapgSaveDto {
    private String id;
    private String name;
    private Set<String> sections;

}
