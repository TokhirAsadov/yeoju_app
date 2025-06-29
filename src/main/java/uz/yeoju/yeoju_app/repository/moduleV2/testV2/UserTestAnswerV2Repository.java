package uz.yeoju.yeoju_app.repository.moduleV2.testV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;

import java.util.Optional;

public interface UserTestAnswerV2Repository extends JpaRepository<UserTestAnswerV2,String> {
    boolean existsByUserIdAndQuestionV2Id(String userId, String questionV2Id);

    Optional<UserTestAnswerV2> findByUserAndQuestionV2(User user, TestQuestionV2 questionV2);
}
