package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import java.util.List;

public class RoomStatistics {
    private String room;
    private List<TimesForRoom> times;
    public String getRoom() { return room; }
    public List<TimesForRoom> getTimes() { return times; }
}
