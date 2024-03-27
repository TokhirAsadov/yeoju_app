package uz.yeoju.yeoju_app.payload.resDto.module;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Set;

public interface GetThemesItems {
    String getId();
    String getName();
    Double getMaxGrade();
    String getGroupId();
    String getLessonId();
    String getEducationYearId();
    String getStudentId();
    String getStudentName();
    String getCreatedBy();
    Timestamp getCreatedAt();

    @Value("#{@gradeOfStudentByTeacherRepository.getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonId(target.createdBy, target.studentId, target.educationYearId, target.lessonId)}")
    Set<GetGradesOfStudent> getGrades();
}
