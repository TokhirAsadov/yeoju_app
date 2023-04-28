package uz.yeoju.yeoju_app.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {


    private String lastName;
    private String firstName;
    private String middleName;

    private String userId;
    private String fullName;

    private String login;

    private String password;

//    @Column()
    private String RFID;


    private String email;
    private String passportNum;
    private Date bornYear;
    private String citizenship;//fuqaroligi
    private String nationality;//millati

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    private Gander gander;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Position> positions;

    @Column
    private Boolean accountNonExpired = true;
    @Column
    private Boolean accountNonLocked = true;
    @Column
    private Boolean credentialsNonExpired = true;
    @Column
    private Boolean enabled = true;



    public User(String id, String fullName, String login, String password) {
        super(id);
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public User(String id, String fullName, String login, String password, String RFID, String email,  Set<Role> roles) {
        super(id);
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.passportNum = passportNum;
        this.roles = roles;
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

    public User(String id, String userId, String fullName, String login, String password, String RFID, String email, Gander gander, Set<Role> roles) {
        super(id);
        this.userId = userId;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.gander = gander;
        this.roles = roles;
    }


    public User(String id, String fullName, String login, String password, String RFID, String email, Gander gander, Set<Role> roles) {
        super(id);
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.gander = gander;
        this.roles = roles;
    }

    public User(String fullName, String login, String password, String RFID, String email, Set<Role> roles) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.roles = roles;
    }

    public User(String fullName, String login, String password, String RFID, String email, Gander gander, Set<Role> roles) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.gander = gander;
        this.roles = roles;
    }

    public User(String userId, String fullName, String RFID) {
        this.userId = userId;
        this.fullName = fullName;
        this.RFID = RFID;
    }

    public User(String userId, String fullName, String RFID, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.userId = userId;
        this.fullName = fullName;
        this.RFID = RFID;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

}
