package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionResponse;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseTestRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestQuestionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestQuestionImplService implements TestQuestionService {
    private final CourseTestRepository courseTestRepository;
    private final TestQuestionRepository testQuestionRepository;

    public TestQuestionImplService(CourseTestRepository courseTestRepository, TestQuestionRepository testQuestionRepository) {
        this.courseTestRepository = courseTestRepository;
        this.testQuestionRepository = testQuestionRepository;
    }


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
        testQuestion.setOptions(creator.options);
        testQuestion.setCorrectAnswerText(creator.correctAnswerText);
        testQuestion.setTest(courseTestRepository.getById(creator.courseTestId));
        TestQuestion save = testQuestionRepository.save(testQuestion);
        return new ApiResponse(true,"Test Question created successfully",save.getId());
    }



}
