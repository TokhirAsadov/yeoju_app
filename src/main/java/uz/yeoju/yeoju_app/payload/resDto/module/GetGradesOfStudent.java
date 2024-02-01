package uz.yeoju.yeoju_app.payload.resDto.module;

import java.sql.Timestamp;

public interface GetGradesOfStudent {
    String getId();
    Float getGrade();
    Timestamp getTime();
    Timestamp getCreatedAt();
    String getDescription();
}
