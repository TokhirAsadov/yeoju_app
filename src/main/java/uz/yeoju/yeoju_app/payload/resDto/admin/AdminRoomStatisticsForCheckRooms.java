package uz.yeoju.yeoju_app.payload.resDto.admin;

import java.time.LocalDateTime;

public interface AdminRoomStatisticsForCheckRooms {
    LocalDateTime getTime();
    Integer getPost();
    String getRoom();
}
