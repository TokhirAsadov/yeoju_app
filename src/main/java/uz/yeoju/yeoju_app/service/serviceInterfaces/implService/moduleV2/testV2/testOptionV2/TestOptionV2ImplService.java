package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testOptionV2;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestOptionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.UserTestAnswerV2Finisher;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.UserTestAnswerV2FinisherChild;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestOptionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.UserTestAnswerV2Repository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestOptionV2ImplService implements TestOptionV2Service {
    private final TestOptionV2Repository testOptionV2Repository;
    private final UserRepository userRepository;
    private final TestQuestionV2Repository testQuestionV2Repository;
    private final UserTestAnswerV2Repository userTestAnswerV2Repository;
    private final TestV2Repository testV2Repository;

    public TestOptionV2ImplService(TestOptionV2Repository testOptionV2Repository, UserRepository userRepository, TestQuestionV2Repository testQuestionV2Repository, UserTestAnswerV2Repository userTestAnswerV2Repository, TestV2Repository testV2Repository) {
        this.testOptionV2Repository = testOptionV2Repository;
        this.userRepository = userRepository;
        this.testQuestionV2Repository = testQuestionV2Repository;
        this.userTestAnswerV2Repository = userTestAnswerV2Repository;
        this.testV2Repository = testV2Repository;
    }

    @Override
    public ApiResponse finishCourseTest(UserTestAnswerV2Finisher finisher) {
        if (finisher.testId == null || finisher.testId.isEmpty()) {
            return new ApiResponse(false, "Test ID is required");
        }
        TestV2 test = testV2Repository.findById(finisher.testId)
                .orElseThrow(() -> new RuntimeException("Test not found by id = " + finisher.testId));
        if (!userRepository.existsById(finisher.userId)) {
            return new ApiResponse(false, "User not found by id = " + finisher.userId);
        }

        User user = userRepository.getById(finisher.userId);

        for (UserTestAnswerV2FinisherChild answer : finisher.answers) {
            if (!testQuestionV2Repository.existsById(answer.testQuestionId)) {
                throw new RuntimeException("Test question not found by id = " + answer.testQuestionId);
            }

//            if (userTestAnswerV2Repository.existsByUserIdAndQuestionV2Id(finisher.userId, answer.testQuestionId)) {
//                throw new RuntimeException("Bunday javob allaqachon mavjud!");
//            }

            TestQuestionV2 question = testQuestionV2Repository.getById(answer.testQuestionId);

            UserTestAnswerV2 userTestAnswer = new UserTestAnswerV2();
            userTestAnswer.setUser(user);
            userTestAnswer.setQuestionV2(question);
            userTestAnswer.setTestV2(test);

            if (question.getType() == TestType.WRITTEN) {
                // WRITTEN testlar
                userTestAnswer.setWrittenAnswer(answer.getWrittenAnswer());
                userTestAnswer.setCorrect(false); // hali baholanmagan
                userTestAnswer.setScore(0); // teacher keyinchalik baho beradi
            } else {
                // SINGLE / MULTIPLE CHOICE
                List<TestOptionV2> selectedOptions = testOptionV2Repository.findAllById(answer.getSelectedOptionsIds());

                List<String> correctOptionIds = question.getOptions().stream()
                        .filter(TestOptionV2::getCorrect)
                        .map(opt -> opt.getId().trim())
                        .collect(Collectors.toList());

                List<String> selectedOptionIds = selectedOptions.stream()
                        .map(opt -> opt.getId().trim())
                        .collect(Collectors.toList());

                boolean isCorrect = new HashSet<>(correctOptionIds).equals(new HashSet<>(selectedOptionIds));

                int score = 0;
                if (isCorrect) {
                    if (question.getType() == TestType.SINGLE_CHOICE) {
                        score = test.getSingleChoiceBall();
                    } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                        score = test.getMultipleChoiceBall();
                    }
                }
                userTestAnswer.setSelectedOptionsV2(selectedOptions);
                userTestAnswer.setCorrect(isCorrect);
                userTestAnswer.setScore(score);
            }

            userTestAnswerV2Repository.save(userTestAnswer);
        }

        return new ApiResponse(true, "User test answers saved successfully");
    }
}
