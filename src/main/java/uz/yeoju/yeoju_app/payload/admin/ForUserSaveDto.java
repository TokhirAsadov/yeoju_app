package uz.yeoju.yeoju_app.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Gander;
import uz.yeoju.yeoju_app.entity.enums.Gandername;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForUserSaveDto {
    private String id;
    private String address;
    private String district;
    private String firstName;
    private Gandername gander;
    private String lastName;
    private String login;
    private String middleName;
    private String passport;
    private String password;
    private String region;
    private String rfid;
    private List<String> roles;
    private List<String> positions;
}