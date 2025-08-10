package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testQuestionV2;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.GiveScoreToWrittenUserAnswerDto;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestQuestionV2Creator;

public interface TestQuestionV2Service {
    ApiResponse createTestQuestionV2(TestQuestionV2Creator creator);
    ApiResponse checkEnoughQuestions(String courseId, int count, TestType type);
    ApiResponse getCountOfTypeOfQuestionsByCourseId(String courseId);
    ApiResponse updateTestQuestionV2(String questionId, TestQuestionV2Creator creator);
    ApiResponse checkEnoughQuestionsForModule(String moduleId, int count, TestType type);

}
