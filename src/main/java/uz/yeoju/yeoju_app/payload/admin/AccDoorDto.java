package uz.yeoju.yeoju_app.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccDoorDto {
    private Long id;
    private Long deviceId;
    private Integer doorNo;
    private String doorName;
}
