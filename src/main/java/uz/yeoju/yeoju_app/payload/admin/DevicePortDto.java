package uz.yeoju.yeoju_app.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevicePortDto {
    private String id;
    private Integer port;

    public DevicePortDto(Integer port) {
        this.port = port;
    }
}
