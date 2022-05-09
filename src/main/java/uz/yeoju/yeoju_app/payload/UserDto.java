package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String fio;
    private String login;
    private String password;
    private String RFID;
    private String email;
    private GanderDto ganderDto;
    private Set<RoleDto> roleDtos;



    public UserDto(String fio, String login, String password, String RFID, String email, GanderDto ganderDto, Set<RoleDto> roleDtos) {
        this.fio = fio;
        this.login = login;
        this.password = password;
        this.RFID = RFID;
        this.email = email;
        this.ganderDto = ganderDto;
        this.roleDtos = roleDtos;
    }
}
