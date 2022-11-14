package uz.yeoju.yeoju_app.entity.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KafedraMudiri extends AbsEntity {

    @OneToOne
    private User user;

    @OneToOne
    private Kafedra kafedra;

    public KafedraMudiri(String id, User user, Kafedra kafedra) {
        super(id);
        this.user = user;
        this.kafedra = kafedra;
    }
}
