package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface RoomsForStatistics2 {

    String getId();
    String getRoom();
    Integer getYear();
    Integer getWeek();
    Integer getWeekday();

    @Value("#{@userRepository.getTimesForRoomStatisticsByWeek(target.id, target.room,target.week,target.year,target.weekday)}")
    List<TimesForRoom> getTimes();
}
