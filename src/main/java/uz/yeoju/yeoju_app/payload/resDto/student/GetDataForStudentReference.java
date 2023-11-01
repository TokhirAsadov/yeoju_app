package uz.yeoju.yeoju_app.payload.resDto.student;

import java.sql.Timestamp;

public interface GetDataForStudentReference {
    String getId();
    Timestamp getTime();
    Long getQueue();
    Long getNumeration();
    String getFullName();
    String getPassport();
    String getDirection();
    Integer getGrade();
    String getEduLang();
    String getEduType();
    String getLengthOfStudying();
    String getRektororder();
    String getDean();
}
