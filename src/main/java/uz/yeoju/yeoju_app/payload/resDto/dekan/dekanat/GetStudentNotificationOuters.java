package uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat;

import java.sql.Timestamp;
import java.util.Date;

public interface GetStudentNotificationOuters {
    String getId();
    String getDynamicSection();
    String getFullName();
    String getGroupName();
    String getDirection();
    Integer getLevel();
    Long getQueue();
    Long getStudentQueue();
    String getEducationYear();
    Date getFromDate();
    Date getToDate();
    Timestamp getCreatedAt();
}
