package uz.yeoju.yeoju_app.payload.resDto.module.vedimost;

import java.sql.Timestamp;

public interface GetVedimostOfKafedra {
    String getId();
    Integer getLevel();
    Timestamp getDeadline();
    Timestamp getTimeClose();
    String getCondition();
    String getTeacher();
    String getLesson();
    String getGroupName();
    String getHeadOfDepartment();
    String getCourseLeader();
    String getHeadOfAcademicAffair();
    String getDirection();
}
