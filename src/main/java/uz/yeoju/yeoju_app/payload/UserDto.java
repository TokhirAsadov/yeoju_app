package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String fullName;
    private String login;
    private String password;
    private String RFID;
    private String email;
    private Date bornYear;

    private String citizenship;//fuqaroligi
    private String nationality;//millati
    private GanderDto ganderDto;
    private Set<RoleDto> roleDtos;



    public UserDto(String fullName, String login, String password, String RFID, String email, GanderDto ganderDto, Set<RoleDto> roleDtos) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.ganderDto = ganderDto;
        this.roleDtos = roleDtos;
    }


    public UserDto(String fullName, String login, String password, String RFID, String email, Timestamp bornYear, String citizenship, String nationality, GanderDto ganderDto, Set<RoleDto> roleDtos) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.bornYear = bornYear;
        this.citizenship = citizenship;
        this.nationality = nationality;
        this.ganderDto = ganderDto;
        this.roleDtos = roleDtos;
    }
}
