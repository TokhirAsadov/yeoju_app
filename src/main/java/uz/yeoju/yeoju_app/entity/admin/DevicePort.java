package uz.yeoju.yeoju_app.entity.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DevicePort extends AbsEntity {
    private Integer port;

    public DevicePort(String id, Integer port) {
        super(id);
        this.port = port;
    }
}
