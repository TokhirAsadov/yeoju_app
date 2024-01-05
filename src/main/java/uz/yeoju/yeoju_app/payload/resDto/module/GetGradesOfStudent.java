package uz.yeoju.yeoju_app.payload.resDto.module;

import java.sql.Timestamp;

public interface GetGradesOfStudent {
    String getId();
    Integer getGrade();
    Timestamp getTime();
    Timestamp getCreatedAt();
    String getDescription();
}
