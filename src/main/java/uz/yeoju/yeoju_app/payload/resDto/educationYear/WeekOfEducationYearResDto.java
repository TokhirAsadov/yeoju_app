package uz.yeoju.yeoju_app.payload.resDto.educationYear;

import java.util.Date;

public interface WeekOfEducationYearResDto {

    String getId();
    Date getStart();
    Date getEnd();
    String getType();
    Integer getSortNumber();
    Integer getWeekNumber();
    String getEduType();
    Integer getCourse();
}
