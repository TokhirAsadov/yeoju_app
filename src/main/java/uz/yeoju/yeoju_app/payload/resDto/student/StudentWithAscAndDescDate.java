package uz.yeoju.yeoju_app.payload.resDto.student;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public interface StudentWithAscAndDescDate {
    String getCardNo();
    Date getTimeAsc();
    Date getTimeDesc();
}
