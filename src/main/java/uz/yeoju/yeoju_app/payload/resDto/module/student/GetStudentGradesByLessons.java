package uz.yeoju.yeoju_app.payload.resDto.module.student;

import org.springframework.beans.factory.annotation.Value;

public interface GetStudentGradesByLessons {
    String getStudentId();
    String getEducationYearId();
    String getGroupId();
    String getLessonId();
    String getLessonName();

    String getTeacherId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getFullName();


}
