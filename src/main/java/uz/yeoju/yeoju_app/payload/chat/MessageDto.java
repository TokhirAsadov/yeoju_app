package uz.yeoju.yeoju_app.payload.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.yeoju.yeoju_app.payload.UserDto;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {
    private String id;
    private ChatDto chatDto;
    private UserDto sender;
    private String text;

    public MessageDto(ChatDto chatDto, UserDto sender, String text) {
        this.chatDto = chatDto;
        this.sender = sender;
        this.text = text;
    }
}
