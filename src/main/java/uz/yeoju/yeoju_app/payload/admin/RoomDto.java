package uz.yeoju.yeoju_app.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private String id;
    private String name;

    public RoomDto(String name) {
        this.name = name;
    }
}
