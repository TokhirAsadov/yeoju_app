package uz.yeoju.yeoju_app.payload.resDto.chat;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.chat.Chat;

public interface ChatRes {
    String getId();

    @Value("#{@chatRepository.forRes(target.id)}")
    Chat getChat();
}
