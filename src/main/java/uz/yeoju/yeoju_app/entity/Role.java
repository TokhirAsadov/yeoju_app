package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbsEntity implements GrantedAuthority {

    @Column
    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public Role(String id, String roleName) {
        super(id);
        this.roleName = roleName;
    }

    public Role(String id, String roleName, Section section) {
        super(id);
        this.roleName = roleName;
        this.section = section;
    }
}
