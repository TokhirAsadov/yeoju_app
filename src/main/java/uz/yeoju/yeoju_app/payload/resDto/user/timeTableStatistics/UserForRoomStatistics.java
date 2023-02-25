package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserForRoomStatistics {

    String getId();
    String getFullName();
    String getRFID();
    String getPassport();

    @Value("#{@userRepository.getRoomsForStatistics(target.id)}")
    List<RoomsForStatistics> getRooms();
}
