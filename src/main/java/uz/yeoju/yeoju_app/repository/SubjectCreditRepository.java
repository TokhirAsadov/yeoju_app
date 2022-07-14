package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.studentBall.SubjectCredit;

import java.util.List;

public interface SubjectCreditRepository extends JpaRepository<SubjectCredit, Long> {
    List<SubjectCredit> getSubjectCreditsByGroupName(String group_name);
    List<SubjectCredit> getSubjectCreditsByLessonName(String lesson_name);

    boolean existsSubjectCreditByLessonNameAndGroupName(String lesson_name, String group_name);
    SubjectCredit getSubjectCreditByLessonNameAndGroupName(String lesson_name, String group_name);
}
