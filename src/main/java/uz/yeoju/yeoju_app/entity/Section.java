package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Section extends AbsEntity {
    @Column(unique = true)
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    private User owner;

    private String room;
    private String phone;

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

    public Section(String id, String name, User owner, String room, String phone, Set<Role> roles, Set<Position> positions) {
        super(id);
        this.name = name;
        this.owner = owner;
        this.room = room;
        this.phone = phone;
        this.roles = roles;
        this.positions = positions;
    }
}
