package uz.yeoju.yeoju_app.repository.moduleV2.testV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;

import java.sql.Timestamp;
import java.util.List;

public interface UserTestAnswerV2Repository extends JpaRepository<UserTestAnswerV2,String> {
    List<UserTestAnswerV2> findAllByUserIdAndTestV2Id(String userId, String testId);

    List<UserTestAnswerV2> findAllByTestV2Id(String testId);

    List<UserTestAnswerV2> findAllByShouldBeDeletedTrueAndCreatedAtBefore(Timestamp twoHoursAgo);

    @Modifying
    @Query(value = "DELETE FROM UserTestAnswerSelectedOptionsV2 WHERE test_option_v2_id IN (SELECT id FROM TestOptionV2 WHERE question_v2_id = :questionId)", nativeQuery = true)
    void deleteAllSelectedOptionsByQuestionId(@Param("questionId") String questionId);

}
