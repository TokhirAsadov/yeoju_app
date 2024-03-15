package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;

import java.util.Set;

public interface ThemeOfSubjectForGradeByTeacherRepository extends JpaRepository<ThemeOfSubjectForGradeByTeacher, String> {

    Boolean existsByNameAndLessonIdAndEducationYearIdAndCreatedBy(String name, String lesson_id, String educationYear_id, String createdBy);
    Set<ThemeOfSubjectForGradeByTeacher> findAllByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String group_id, String lesson_id, String educationYear_id, String createdBy);

    ThemeOfSubjectForGradeByTeacher findFirstByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String group_id, String lesson_id, String educationYear_id, String createdBy);

}
