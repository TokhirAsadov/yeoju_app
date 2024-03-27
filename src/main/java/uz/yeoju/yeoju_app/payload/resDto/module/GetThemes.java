package uz.yeoju.yeoju_app.payload.resDto.module;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Set;

public interface GetThemes {
    String getId();
    String getName();
    Double getMaxGrade();
    String getGroupId();
    String getLessonId();
    String getEducationYearId();
    String getCreatedBy();
    Timestamp getCreatedAt();

    @Value("#{@themeOfSubjectForGradeByTeacherRepository.getThemesItems(target.id,target.groupId,target.lessonId,target.educationYearId,target.createdBy)}")
    Set<GetThemesItems> getStudents();
}
