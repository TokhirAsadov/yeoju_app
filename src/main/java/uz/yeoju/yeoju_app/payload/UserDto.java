package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
