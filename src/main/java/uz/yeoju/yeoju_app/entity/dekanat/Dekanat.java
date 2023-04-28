package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dekanat extends AbsEntity {

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Faculty> faculties;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Position> positions;

    public Dekanat(String id, String name) {
        super(id);
        this.name = name;
    }

    public Dekanat(String id, String name, Set<Faculty> faculties, Set<Role> roles, Set<Position> positions) {
        super(id);
        this.name = name;
        this.faculties = faculties;
        this.roles = roles;
        this.positions = positions;
    }
}
