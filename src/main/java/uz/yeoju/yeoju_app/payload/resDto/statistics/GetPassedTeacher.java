package uz.yeoju.yeoju_app.payload.resDto.statistics;

import java.util.Date;

public interface GetPassedTeacher {
    String getKafedra();
    String getFullName();
    String getLogin();
    String getClassroom();
    Date getTime();
    Integer getSection();

}
