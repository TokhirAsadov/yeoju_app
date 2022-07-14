package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsLongEntity;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Position extends AbsLongEntity {
    private String userPositionName;

    public Position(Long id, String userPositionName) {
        super(id);
        this.userPositionName = userPositionName;
    }
}
