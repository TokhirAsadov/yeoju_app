package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface RoomsForWeekStatisticsByWeek {

    String getId();
    Integer getWeek();
    Integer getYear();
    Integer getWeekday();
    String getRoom();

    @Value("#{@userRepository.getTimesForRoomStatisticsByWeek(target.id, target.room,target.week,target.year,target.weekday)}")
    List<TimesForRoom> getTimes();
}
