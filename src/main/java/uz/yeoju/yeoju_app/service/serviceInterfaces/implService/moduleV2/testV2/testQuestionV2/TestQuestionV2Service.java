package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testQuestionV2;

import uz.yeoju.yeoju_app.entity.moduleV2.TestType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestQuestionV2Creator;

public interface TestQuestionV2Service {
    ApiResponse createTestQuestionV2(TestQuestionV2Creator creator);
    ApiResponse checkEnoughQuestions(String courseId, int count, TestType type);
    ApiResponse getCountOfTypeOfQuestionsByCourseId(String courseId);
}
