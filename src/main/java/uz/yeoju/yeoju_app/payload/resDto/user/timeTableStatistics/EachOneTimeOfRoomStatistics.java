package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import java.util.Date;

public interface EachOneTimeOfRoomStatistics {
    String getId();
    String getFullName();
    Date getTime();
    Integer getSection();
    String getRoom();
}
