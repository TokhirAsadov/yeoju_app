package uz.yeoju.yeoju_app.entity.uquvbulim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOfLastActive extends AbsEntity {
    @ManyToOne
    private User user;

    public DataOfLastActive(String id, User user) {
        super(id);
        this.user = user;
    }
}
