package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public interface StudentsDynamicAttendance {
    String getNameId();
    String getType();
//    @JsonIgnore
    String getCreatedBy();
    Boolean getIsCome();
    Date getTime();
    Integer getWeek();
    Integer getWeekday();
    Integer getSection();
}
