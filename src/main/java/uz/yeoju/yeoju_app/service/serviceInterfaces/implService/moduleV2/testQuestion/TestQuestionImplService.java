package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.TestOption;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseTestRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestOptionRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestQuestionRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestQuestionImplService implements TestQuestionService {
    private final CourseTestRepository courseTestRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final TestOptionRepository testOptionRepository;

    public TestQuestionImplService(CourseTestRepository courseTestRepository, TestQuestionRepository testQuestionRepository, TestOptionRepository testOptionRepository) {
        this.courseTestRepository = courseTestRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testOptionRepository = testOptionRepository;
    }


    @Deprecated
    @Override
    @Transactional
    public ApiResponse create(TestQuestionCreator creator) {
        if (!courseTestRepository.existsById(creator.courseTestId)) {
            return new ApiResponse(false,"Course test not found by id="+creator.courseTestId);
        }
        Integer exists = testQuestionRepository.existsByNormalizedQuestionText(creator.questionText);
        if (exists != null && exists == 1) {
            return new ApiResponse(false, "Bunday savol allaqachon mavjud!");
        }

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setQuestionText(creator.questionText);
//        testQuestion.setOptions(creator.options);
//        testQuestion.setCorrectAnswers(creator.correctAnswers);
        testQuestion.setTest(courseTestRepository.getById(creator.courseTestId));
        testQuestion.setType(creator.type);
        TestQuestion save = testQuestionRepository.save(testQuestion);
        return new ApiResponse(true,"Test Question created successfully",save.getId());
    }

    @Override
    @Transactional
    public ApiResponse createV2(TestQuestionCreatorV2 creator) {

        if (!courseTestRepository.existsById(creator.courseTestId)) {
            return new ApiResponse(false,"Course test not found by id="+creator.courseTestId);
        }
        Integer exists = testQuestionRepository.existsByNormalizedQuestionText(creator.questionText);
        if (exists != null && exists == 1) {
            return new ApiResponse(false, "Bunday savol allaqachon mavjud!");
        }

        Set<String> normalizedTexts = new HashSet<>();
        for (TestOptionCreatorV2 option : creator.getOptions()) {
            String normalized = option.getText().trim().toLowerCase();
            if (!normalizedTexts.add(normalized)) {
                return new ApiResponse(false, "Option '" + option.getText() + "' takrorlangan (ignoring case and spaces)");
            }
        }

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setQuestionText(creator.questionText);
        testQuestion.setTest(courseTestRepository.getById(creator.courseTestId));
        testQuestion.setType(creator.type);
        TestQuestion save = testQuestionRepository.save(testQuestion);

        creator.options.forEach(option -> {
            TestOption testOption = new TestOption();
            testOption.setQuestion(save);
            testOption.setText(option.text);
            testOption.setCorrect(option.isCorrect);
            testOption.setScore(option.score);
            testOptionRepository.save(testOption);
        });

        return new ApiResponse(true, "Test savoli va variantlar muvaffaqiyatli saqlandi");
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Course Test",testQuestionRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String testId) {
        TestQuestion testQuestion = testQuestionRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test question not found by id="+testId));
        return new ApiResponse(true,"Test question by id="+testId,testQuestion);
    }

    @Override
    public boolean deleteById(String testQuestionId) {
        TestQuestion testQuestion = testQuestionRepository.findById(testQuestionId)
                .orElseThrow(() -> new RuntimeException("Test question not found by id=" + testQuestionId));
        testOptionRepository.deleteAll(testQuestion.getOptions());
//        // qo‘lda options’ni o‘chirib yuborish
//        testQuestion.setOptions(new ArrayList<>()); // yoki null
////        testQuestion.setCorrectAnswers(new ArrayList<>()); // yoki null
//        testQuestionRepository.save(testQuestion); // majburiy emas, lekin ehtiyot chorasi

        testQuestionRepository.delete(testQuestion);
        return true;
    }

    @Override
    public ApiResponse findTestQuestionsByCourseIdWithShuffledOptions(String courseTestId) {
        List<TestQuestion> testQuestions = testQuestionRepository.findAllByTestId(courseTestId);
        if (testQuestions.isEmpty()) {
            return new ApiResponse(false, "Test questions not found by course test id=" + courseTestId);
        }
        Set<TestQuestionResponseV2> collect = testQuestions.stream().map(test -> {
            List<TestOptionResponseV2> shuffledOptions = new ArrayList<>(test.getOptions().stream().map(option-> new TestOptionResponseV2(option.getId(),option.getText())).collect(Collectors.toList()));
            Collections.shuffle(shuffledOptions);
            return new TestQuestionResponseV2(
                    test.getId(),
                    test.getType(),
                    test.getTest().getId(),
                    test.getQuestionText(),
                    shuffledOptions
            );
        }).collect(Collectors.toSet());
        return new ApiResponse(true, "Test questions by course test id=" + courseTestId, collect);
    }

    @Override
    public ApiResponse findByCourseTestId(String courseTestId) {
        List<TestQuestion> testQuestions = testQuestionRepository.findAllByTestId(courseTestId);
        if (testQuestions.isEmpty()) {
            return new ApiResponse(false, "Test questions not found by course test id=" + courseTestId);
        }
        Set<TestQuestionResponse> collect = testQuestions.stream().map(test -> {
            return new TestQuestionResponse(
                    test.getId(),
                    test.getTest().getId(),
                    test.getQuestionText(),
                    test.getType(),
                    test.getOptions().stream().map(option -> new TestOptionResponse(
                            option.getId(),
                            option.getText(),
                            option.getScore(),
                            option.getCorrect()
                    )).collect(Collectors.toList())
            );
        }).collect(Collectors.toSet());
        return new ApiResponse(true, "Test questions by course test id=" + courseTestId, collect);
    }

}
