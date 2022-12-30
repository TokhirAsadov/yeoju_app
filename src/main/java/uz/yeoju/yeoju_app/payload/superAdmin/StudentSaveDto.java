package uz.yeoju.yeoju_app.payload.superAdmin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSaveDto {
    private String fullName;
    private String rfid;
    private String login;
    private String passport;
    private String password;
    private String group;
    private Integer level;
}
