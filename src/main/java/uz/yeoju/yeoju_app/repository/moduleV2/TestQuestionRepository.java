package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;

import java.util.List;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,String> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM TestQuestion WHERE LOWER(TRIM(questionText)) = LOWER(TRIM(:questionText))", nativeQuery = true)
    Integer existsByNormalizedQuestionText(@Param("questionText") String questionText);

    List<TestQuestion> findAllByTestId(String testId);

}
