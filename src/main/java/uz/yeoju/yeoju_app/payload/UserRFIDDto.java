package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.Gandername;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

//@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRFIDDto {
    private String id;
    private String fullName;
    private String login;
    private String RFID;
    private String roleDtos;



    public UserRFIDDto(String fullName, String login, String RFID, String roleDtos) {
        this.fullName = fullName;
        this.login = login;
        this.RFID = RFID;
        this.roleDtos = roleDtos;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public String getRoleDtos() {
        return roleDtos;
    }

    public void setRoleDtos(String roleDtos) {
        this.roleDtos = roleDtos;
    }

    @Override
    public String toString() {
        return "UserRFIDDto{" +
                "fullName='" + fullName + '\'' +
                ", login='" + login + '\'' +
                ", RFID='" + RFID + '\'' +
                ", roleDtos=" + roleDtos +
                '}';
    }
}
