package uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat;

import java.sql.Timestamp;
import java.util.Date;

public interface GetStudentReferrals {
    String getId();
    Long getNumberOfDecision();
    Date getDecisionDate();
    String getFullName();
    String getGroupName();
    String getDirection();
    Integer getLevel();
    Long getQueue();
    Long getStudentQueue();
    Date getFromDate();
    Date getToDate();
    Timestamp getCreatedAt();
}
