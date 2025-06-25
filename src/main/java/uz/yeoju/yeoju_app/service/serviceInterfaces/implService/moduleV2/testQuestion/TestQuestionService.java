package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.GiveScoreToWrittenUserAnswerDto;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreatorV2;

public interface TestQuestionService {
    @Deprecated
    ApiResponse create(TestQuestionCreator creator);
    ApiResponse createV2(TestQuestionCreatorV2 creator);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String testQuestionId);
    boolean deleteById(String testQuestionId);
    ApiResponse findTestQuestionsByCourseIdWithShuffledOptions(String courseId);
    ApiResponse findByCourseTestId(String courseTestId);

    ApiResponse getStudentCourseTestAnswers(String testId, String studentId);

    ApiResponse giveScoreToWrittenUserAnswer(GiveScoreToWrittenUserAnswerDto dto);
}
