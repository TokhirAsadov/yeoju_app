package uz.yeoju.yeoju_app.entity.uquvbulim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOfLastActive extends AbsEntity {
    private String passage;
}
