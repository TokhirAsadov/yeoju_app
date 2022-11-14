package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserOfSectionOrKafedra extends AbsEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Section section;

    @ManyToOne
    private Kafedra kafedra;

    public UserOfSectionOrKafedra(String id, User user, Section section) {
        super(id);
        this.user = user;
        this.section = section;
    }

    public UserOfSectionOrKafedra(String id, User user, Kafedra kafedra) {
        super(id);
        this.user = user;
        this.kafedra = kafedra;
    }
}
