package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;

import java.util.Set;

public interface ThemeOfSubjectForGradeByTeacherRepository extends JpaRepository<ThemeOfSubjectForGradeByTeacher, String> {
    Set<ThemeOfSubjectForGradeByTeacher> findAllByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String lesson_id, String educationYear_id, String createdBy);

}
