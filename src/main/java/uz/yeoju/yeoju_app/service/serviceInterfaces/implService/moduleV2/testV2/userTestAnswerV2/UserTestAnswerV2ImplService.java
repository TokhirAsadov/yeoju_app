package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.userTestAnswerV2;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;
import uz.yeoju.yeoju_app.payload.moduleV2.TestResultDto;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.UserTestAnswerV2Repository;

import java.util.List;

@Service
public class UserTestAnswerV2ImplService implements UserTestAnswerV2Service {
    private final UserTestAnswerV2Repository userTestAnswerV2Repository;
    private final TestQuestionV2Repository testQuestionV2Repository;

    public UserTestAnswerV2ImplService(UserTestAnswerV2Repository userTestAnswerV2Repository, TestQuestionV2Repository testQuestionV2Repository) {
        this.userTestAnswerV2Repository = userTestAnswerV2Repository;
        this.testQuestionV2Repository = testQuestionV2Repository;
    }

    @Override
    public TestResultDto calculateUserScore(String studentUserId, String testId) {
        List<UserTestAnswerV2> userAnswers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(studentUserId, testId);
        int totalQuestions = userAnswers.size();

        int correctCount = (int) userAnswers.stream()
                .filter(UserTestAnswerV2::isCorrect)
                .count();

        return new TestResultDto(totalQuestions, correctCount);
    }
}
