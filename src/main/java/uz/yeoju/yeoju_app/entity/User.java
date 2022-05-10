package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    private String fio;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(unique = true)
    private String RFID;

    @Column(unique = true)
    private String email;

    @OneToOne
    private Gander gander;

    @ManyToMany
    private Set<Role> roles;

    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;

    public User(UUID id, String fio, String login, String password) {
        super(id);
        this.fio = fio;
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String fio, String login, String password, String RFID, String email, Gander gander, Set<Role> roles) {
        this.fio = fio;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.gander = gander;
        this.roles = roles;
    }
}
