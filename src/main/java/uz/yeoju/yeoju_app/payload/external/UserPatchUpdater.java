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
}
