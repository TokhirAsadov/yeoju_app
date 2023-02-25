package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface RoomsForWeekStatistics {

    String getId();
    Integer getWeekday();
    String getRoom();

    @Value("#{@userRepository.getTimesForRoomStatistics(target.id, target.room,target.weekday)}")
    List<TimesForRoom> getTimes();
}
