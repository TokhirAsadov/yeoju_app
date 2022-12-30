package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.EducationType;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dekan extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Faculty> faculties;

    @OneToOne(fetch = FetchType.LAZY)
    private Dekanat dekanat;

    @ManyToOne(fetch = FetchType.LAZY)
    private EducationType educationType;

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
