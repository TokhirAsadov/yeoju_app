package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Section extends AbsEntity {
    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Position> positions;

    public Section(String id, String name) {
        super(id);
        this.name = name;
    }

    public Section(String name) {
        this.name = name;
    }

    public Section(String id, String name, Set<Role> roles, Set<Position> positions) {
        super(id);
        this.name = name;
        this.roles = roles;
        this.positions = positions;
    }
}
