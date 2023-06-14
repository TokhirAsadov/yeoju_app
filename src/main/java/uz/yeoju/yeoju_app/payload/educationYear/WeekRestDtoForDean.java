package uz.yeoju.yeoju_app.payload.educationYear;

import java.util.Date;

public interface WeekRestDtoForDean {
    String getId();
    Date getStart();
    Integer getSortNumber();
    Integer getCourse();
    String getEduType();
}
