package uz.yeoju.yeoju_app.entity.dekan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dekan extends AbsEntity {

    @OneToOne
    private User user;

    @OneToMany
    private Set<Faculty> faculties;

    public Dekan(String id, User user) {
        super(id);
        this.user = user;
    }

    public Dekan(String id, User user, Set<Faculty> faculties) {
        super(id);
        this.user = user;
        this.faculties = faculties;
    }
}
