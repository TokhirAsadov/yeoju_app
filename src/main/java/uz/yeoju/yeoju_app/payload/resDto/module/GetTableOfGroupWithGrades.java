package uz.yeoju.yeoju_app.payload.resDto.module;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Set;

public interface GetTableOfGroupWithGrades {
    String getTeacherId();
    String getGroupId();
    String getEducationYearId();
    String getThemeId();
    String getThemeName();
    Double getMaxGrade();
    Timestamp getTime();

    @Value("#{@themeOfSubjectForGradeByTeacherRepository.getGradesOfTableOfGroup(target.themeId,target.teacherId,target.educationYearId,target.groupId)}")
    Set<GetGradesOfTableOfGroup> getStudents();
}
