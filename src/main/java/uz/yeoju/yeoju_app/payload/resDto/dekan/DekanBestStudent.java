package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserField;

public interface DekanBestStudent {

    String getId();
    Integer getFails();

    @Value("#{@userRepository.getUserFields(target.id)}")
    UserField getUser();

    @Value("#{@groupRepository.getGroupFieldByUserId(target.id)}")
    StudentGroupField getGroup();
}
