package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;

public interface SearchUserForDekanUseSendMessage {
    String getId();
    String getFullName();

    @Value("#{@groupRepository.getGroupFieldByUserId(target.id)}")
    StudentGroupField getGroupData();
}
