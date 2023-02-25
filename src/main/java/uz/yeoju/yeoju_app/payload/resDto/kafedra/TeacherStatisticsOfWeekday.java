package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import java.util.Date;

public interface TeacherStatisticsOfWeekday {
    String getId();
    String getFullName();
    Date getTime();
    Integer getWeekday();
    Integer getSection();
}
