package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserField;

public interface ForGroupFails {
    String getId();
    Integer getFails();

    @Value("#{@userRepository.getUserFields(target.id)}")
    UserField getUser();
}
