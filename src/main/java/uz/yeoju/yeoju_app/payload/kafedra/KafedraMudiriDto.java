package uz.yeoju.yeoju_app.payload.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafedraMudiriDto {

    private String id;
    private UserDto userDto;
    private KafedraDto kafedraDto;

    public KafedraMudiriDto(UserDto userDto, KafedraDto kafedraDto) {
        this.userDto = userDto;
        this.kafedraDto = kafedraDto;
    }
}
