package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;
import uz.yeoju.yeoju_app.payload.resDto.module.GetThemes;
import uz.yeoju.yeoju_app.payload.resDto.module.GetThemesItems;

import java.util.Set;

public interface ThemeOfSubjectForGradeByTeacherRepository extends JpaRepository<ThemeOfSubjectForGradeByTeacher, String> {

    Boolean existsByNameAndLessonIdAndEducationYearIdAndCreatedBy(String name, String lesson_id, String educationYear_id, String createdBy);
    Set<ThemeOfSubjectForGradeByTeacher> findAllByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String group_id, String lesson_id, String educationYear_id, String createdBy);

    ThemeOfSubjectForGradeByTeacher findFirstByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String group_id, String lesson_id, String educationYear_id, String createdBy);


    //@Query(value = "select * from dbo.GetListStudentsForGetGradesByThemeIdAndGroupIdAndSubjectIdAndTeacherIdAndEducationId(?5,?1,?2,?4,?3) order by createdAt",nativeQuery = true)
    @Query(value = "select * from dbo.GetThemes (?1,?2,?3,?4) order by createdAt",nativeQuery = true)
    Set<GetThemes> getThemes(String groupId, String lessonId, String educationYearId, String teacherId);

    @Query(value = "select * from dbo.GetListStudentsForGetGradesByThemeIdAndGroupIdAndSubjectIdAndTeacherIdAndEducationId(?1,?2,?3,?4,?5) order by createdAt",nativeQuery = true)
    Set<GetThemesItems> getThemesItems(String themeId, String groupId, String lessonId, String educationYearId, String teacherId);

}
