package uz.yeoju.yeoju_app.entity.staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Staff extends AbsEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Section section;

    @ManyToOne(fetch = FetchType.EAGER)
    private Dekanat dekanat;

    public Staff(User user, Section section) {
        this.user = user;
        this.section = section;
    }

    public Staff(String id, User user, Section section) {
        super(id);
        this.user = user;
        this.section = section;
    }
}
