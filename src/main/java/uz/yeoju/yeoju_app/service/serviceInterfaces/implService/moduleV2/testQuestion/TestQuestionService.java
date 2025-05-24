package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.TestQuestionCreator;

public interface TestQuestionService {
    ApiResponse create(TestQuestionCreator creator);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String testQuestionId);
    boolean deleteById(String testQuestionId);

}
