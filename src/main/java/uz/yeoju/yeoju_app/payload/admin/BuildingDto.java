package uz.yeoju.yeoju_app.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto {
    private String id;
    private String name;

    public BuildingDto(String name) {
        this.name = name;
    }
}
