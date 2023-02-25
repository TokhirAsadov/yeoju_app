package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface RoomsForStatistics {

    String getId();
    String getRoom();

    @Value("#{@userRepository.getTimesForRoomStatistics(target.id, target.room)}")
    List<TimesForRoom> getTimes();
}
