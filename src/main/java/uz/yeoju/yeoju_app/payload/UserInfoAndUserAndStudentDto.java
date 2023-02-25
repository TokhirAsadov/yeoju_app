package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndUserAndStudentDto {
    private Long id;
    private String name;
    private String lastname;
    private String cardNo;
    private String login;
    private String password;
    private String passport;
    private String roleName;

    public UserInfoAndUserAndStudentDto(String name, String lastname, String cardNo, String login, String password, String passport, String roleName) {
        this.name = name;
        this.lastname = lastname;
        this.cardNo = cardNo;
        this.login = login;
        this.password = password;
        this.passport = passport;
        this.roleName = roleName;
    }
}
