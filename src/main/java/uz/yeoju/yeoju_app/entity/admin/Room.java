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
public class Room extends AbsEntity {
    private String name;

    public Room(String id, String name) {
        super(id);
        this.name = name;
    }
}
