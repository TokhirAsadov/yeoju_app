package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;

import javax.validation.constraints.Size;
import java.util.Set;

public interface ThemeOfSubjectForGradeByTeacherRepository extends JpaRepository<ThemeOfSubjectForGradeByTeacher, String> {

    Boolean existsByNameAndLessonIdAndEducationYearIdAndCreatedBy(String name, String lesson_id, String educationYear_id, String createdBy);
    Set<ThemeOfSubjectForGradeByTeacher> findAllByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String lesson_id, String educationYear_id, String createdBy);

    ThemeOfSubjectForGradeByTeacher findFirstByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String lesson_id, String educationYear_id, String createdBy);

}
