package uz.yeoju.yeoju_app.entity.ШТАТЛАР;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoleAndPositionGroup extends AbsEntity {

//    @Column(unique = true)

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Section> sections;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Kafedra> kafedras;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Position> positions;

}
