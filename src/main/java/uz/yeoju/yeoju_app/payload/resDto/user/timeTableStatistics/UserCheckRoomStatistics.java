package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;


import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserCheckRoomStatistics {
    String getId();
    String getFullName();
    String getRoom();


    @Value("#{@userRepository.getEachOneTimeOfRoomStatistics(target.id,target.room)}")
    List<EachOneTimeOfRoomStatistics> getTouchTimes();

}
