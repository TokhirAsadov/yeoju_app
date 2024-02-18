package uz.yeoju.yeoju_app.payload.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.yeoju.yeoju_app.payload.UserDto;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ChatDto {
    private String id;
    private Set<UserDto> members;
}
