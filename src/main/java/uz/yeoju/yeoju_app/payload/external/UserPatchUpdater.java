package uz.yeoju.yeoju_app.payload.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPatchUpdater {
    public String firstName;
    public String lastName;
    public String middleName;
    public String login;
    public String password;
    public String firstNameEn;
    public String lastNameEn;
    public String middleNameEn;

    public UserPatchUpdater(String firstName, String lastName, String middleName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.login = login;
    }
}
