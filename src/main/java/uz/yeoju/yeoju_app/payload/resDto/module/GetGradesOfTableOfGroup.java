package uz.yeoju.yeoju_app.payload.resDto.module;

import java.sql.Timestamp;

public interface GetGradesOfTableOfGroup {
    String getGradeId();
    String getStudentId();
    Double getGrade();
    Timestamp getTime();
}
