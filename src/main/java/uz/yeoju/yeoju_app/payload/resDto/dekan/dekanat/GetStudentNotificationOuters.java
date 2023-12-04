package uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat;

import java.sql.Timestamp;
import java.util.Date;

public interface GetStudentNotificationOuters {
    String getId();
    String getEducationYear();
    Date getFromDate();
    Date getToDate();
    Timestamp getUpdatedAt();
}
