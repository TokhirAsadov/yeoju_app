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
public class Building extends AbsEntity {
    private String name;

    private Integer minFloor;
    private Integer maxFloor;

    public Building(String id, String name) {
        super(id);
        this.name = name;
    }
}
