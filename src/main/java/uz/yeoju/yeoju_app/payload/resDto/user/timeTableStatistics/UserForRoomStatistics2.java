package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserForRoomStatistics2 {

    String getId();
    String getFullName();
    String getRFID();
    String getPassport();
    Integer getYear();
    Integer getWeek();
    Integer getWeekday();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,target.year,target.week,target.weekday)}")
    List<RoomsForStatistics2> getRooms();
}
