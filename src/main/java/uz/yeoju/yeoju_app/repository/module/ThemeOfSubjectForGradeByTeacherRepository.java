package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;
import uz.yeoju.yeoju_app.payload.resDto.module.*;

import javax.validation.constraints.Size;
import java.util.Set;

public interface ThemeOfSubjectForGradeByTeacherRepository extends JpaRepository<ThemeOfSubjectForGradeByTeacher, String> {

    Boolean existsByNameAndLessonIdAndEducationYearIdAndCreatedByAndGroupId(@Size(min = 3, message = "Mazvu nomining uzunligi kamida 4 bo`lishi kerak.") String name, String lesson_id, String educationYear_id, String createdBy, String group_id);

    @Query(value = "select id,name,maxGrade from ThemeOfSubjectForGradeByTeacher\n" +
            "where group_id=?1 and lesson_id=?2 and educationYear_id=?3 and createdBy=?4 order by createdAt desc \n",nativeQuery = true)
    Set<GetThemesByQuery> getThemesByQuery(String group_id, String lesson_id, String educationYear_id, String createdBy);

    ThemeOfSubjectForGradeByTeacher findFirstByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String group_id, String lesson_id, String educationYear_id, String createdBy);

    @Query(value = "select gt.id as gradeId,gt.updatedAt as time,gt.student_id as studentId,gt.grade from ThemeOfSubjectForGradeByTeacher th\n" +
            "join GradeOfStudentByTeacher gt on th.id = gt.theme_id\n" +
            "where th.id=?1 and th.active=1 and gt.active=1",nativeQuery = true)
    Set<GetGradesOfTableOfGroup> getGradesByThemeId(String themeId);

    //@Query(value = "select * from dbo.GetListStudentsForGetGradesByThemeIdAndGroupIdAndSubjectIdAndTeacherIdAndEducationId(?5,?1,?2,?4,?3) order by createdAt",nativeQuery = true)
    @Query(value = "select * from dbo.GetThemes (?1,?2,?3,?4) order by createdAt",nativeQuery = true)
    Set<GetThemes> getThemes(String groupId, String lessonId, String educationYearId, String teacherId);

    @Query(value = "select * from dbo.GetListStudentsForGetGradesByThemeIdAndGroupIdAndSubjectIdAndTeacherIdAndEducationId(?1,?2,?3,?4,?5) order by createdAt",nativeQuery = true)
    Set<GetThemesItems> getThemesItems(String themeId, String groupId, String lessonId, String educationYearId, String teacherId);

    @Query(value = "select id as themeId, name as themeName, maxGrade, createdAt as time , ?1 as teacherId,?2 as educationYearId, ?4 as groupId from ThemeOfSubjectForGradeByTeacher where createdBy=?1 and educationYear_id=?2 and lesson_id=?3 and group_id=?4 order by createdAt",nativeQuery = true)
    Set<GetTableOfGroupWithGrades> getTableOfGroup(String teacherId, String educationYearId, String lessonId, String groupId);

    @Query(value = "select gr.id as gradeId, gr.grade, gr.createdAt as time, gr.student_id as studentId from GradeOfStudentByTeacher gr\n" +
            "         join users u on gr.student_id = u.id\n" +
            "         join Student s on u.id = s.user_id\n" +
            "         where gr.active=1 and gr.theme_id=?1 and gr.createdBy=?2 and gr.educationYear_id=?3 and s.group_id=?4 order by gr.createdAt",nativeQuery = true)
    Set<GetGradesOfTableOfGroup> getGradesOfTableOfGroup(String themeId, String teacherId, String educationYearId, String groupId);

    Boolean existsThemeByIdAndCreatedBy(String id, String createdBy);
}
