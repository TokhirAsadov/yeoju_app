package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import java.util.Date;

public interface TeacherStatisticsOfWeekday {
//    String getId();
//    String getRoom();
    String getLogin();
    Date getTime();
    Integer getWeek();
    Integer getWeekday();
    Integer getSection();
}
