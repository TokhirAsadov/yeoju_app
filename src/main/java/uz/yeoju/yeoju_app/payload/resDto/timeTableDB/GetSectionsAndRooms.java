package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.StudentsDynamicAttendance;

import java.util.Date;
import java.util.Set;

public interface GetSectionsAndRooms {
    String getRoom();
    Integer getSection();
}
