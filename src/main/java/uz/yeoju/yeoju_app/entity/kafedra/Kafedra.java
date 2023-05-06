package uz.yeoju.yeoju_app.entity.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kafedra extends AbsEntity {

    private String nameUz;
    private String nameRu;
    private String nameEn;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Position> positions;

    @OneToOne(fetch = FetchType.EAGER)
    private User owner;

    private String room;
    private String phone;

    public Kafedra(String nameUz) {
        this.nameUz = nameUz;
    }

    public Kafedra(String nameUz, String nameRu, String nameEn) {
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    public Kafedra(String id, String nameUz, String nameRu, String nameEn) {
        super(id);
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }

    public Kafedra(String nameUz, String nameRu, String nameEn, Set<Role> roles, Set<Position> positions) {
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.roles = roles;
        this.positions = positions;
    }
}
