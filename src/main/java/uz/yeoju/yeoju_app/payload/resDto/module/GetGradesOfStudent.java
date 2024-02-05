package uz.yeoju.yeoju_app.payload.resDto.module;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Set;

public interface GetGradesOfStudent {
    String getId();
    Float getGrade();
    Timestamp getTime();
    Timestamp getCreatedAt();
    String getDescription();

    @Value("#{@gradeOfStudentByTeacherRepository.getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonIdRetakes(target.id)}")
    Set<GetGradesOfStudent> getRetakes();
}
