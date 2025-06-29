package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testQuestionV2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestOptionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestOptionV2Creator;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestQuestionV2Creator;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestOptionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;

import java.util.HashSet;
import java.util.Set;

@Service
public class TestQuestionV2ImplService implements TestQuestionV2Service{
    private final TestQuestionV2Repository testQuestionV2Repository;
    private final TestOptionV2Repository testOptionV2Repository;
    private final CourseRepository courseRepository;

    public TestQuestionV2ImplService(TestQuestionV2Repository testQuestionV2Repository, TestOptionV2Repository testOptionV2Repository, CourseRepository courseRepository) {
        this.testQuestionV2Repository = testQuestionV2Repository;
        this.testOptionV2Repository = testOptionV2Repository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public ApiResponse createTestQuestionV2(TestQuestionV2Creator creator) {
        if (creator.getCourseId() == null || creator.getCourseId().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (!courseRepository.existsById(creator.getCourseId())) {
            throw new IllegalArgumentException("Course with ID " + creator.getCourseId() + " does not exist");
        }
        if (creator.getQuestionText() == null || creator.getQuestionText().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        if (creator.getOptions() == null || creator.getOptions().isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }

        Set<String> normalizedTexts = new HashSet<>();
        for (TestOptionV2Creator option : creator.getOptions()) {
            String normalized = option.getText().trim().toLowerCase();
            if (!normalizedTexts.add(normalized)) {
                return new ApiResponse(false, "Option '" + option.getText() + "' takrorlangan (ignoring case and spaces)");
            }
        }

        TestQuestionV2 testQuestionV2 = new TestQuestionV2();
        testQuestionV2.setQuestionText(creator.getQuestionText());
        testQuestionV2.setType(creator.getType());
        testQuestionV2.setCourse(courseRepository.findById(creator.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found")));
        TestQuestionV2 save = testQuestionV2Repository.save(testQuestionV2);

        creator.options.forEach(option -> {
            TestOptionV2 testOptionV2 = new TestOptionV2();
            testOptionV2.setText(option.getText());
            testOptionV2.setCorrect(option.isCorrect());
            testOptionV2.setQuestionV2(save);
            testOptionV2Repository.save(testOptionV2);
        });

        return new ApiResponse(true, "Test savoli va variantlar muvaffaqiyatli saqlandi");
    }
}
