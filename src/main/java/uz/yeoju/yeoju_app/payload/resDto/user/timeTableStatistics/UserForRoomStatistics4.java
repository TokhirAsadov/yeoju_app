package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import java.util.List;

public class UserForRoomStatistics4 {
    private String id;
    private List<RoomStatistics> rooms;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<RoomStatistics> getRooms() { return rooms; }
    public void setRooms(List<RoomStatistics> rooms) { this.rooms = rooms; }
}
