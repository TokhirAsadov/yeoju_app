package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    private Long id;
    private String userPositionName;

    public PositionDto(String userPositionName) {
        this.userPositionName = userPositionName;
    }
}
