package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;
import uz.yeoju.yeoju_app.entity.moduleV2.UserTestAnswer;

import java.util.List;
import java.util.Optional;

public interface UserTestAnswerRepository extends JpaRepository<UserTestAnswer,String> {
    boolean existsByUserIdAndQuestionId(String userId, String questionId);

    Optional<UserTestAnswer> findByUserAndQuestion(User user, TestQuestion question);

    List<UserTestAnswer> findAllByUserIdAndQuestionTestId(String userId, String courseTestId);

    List<UserTestAnswer> findAllByQuestionIn(List<TestQuestion> testQuestions);
}
