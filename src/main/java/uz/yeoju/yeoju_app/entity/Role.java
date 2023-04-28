package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends AbsEntity implements GrantedAuthority {

    @Column
    private String roleName;

    private Integer degree;

    @Override
    public String getAuthority() {
        return roleName;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String id, String roleName) {
        super(id);
        this.roleName = roleName;
    }

}
