package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;

import java.io.IOException;

public interface UserTestAnswerService {
    ApiResponse finishCourseTest(UserTestAnswerFinisher finisher);
    ApiResponse create(UserTestAnswerCreator creator);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String userTestAnswerId);
    boolean deleteById(String userTestAnswerId);
    TestResultDto calculateUserScore(String userId, String courseTestId);
    ResponseEntity<AiResponseDto> ai(AiRequestDto dto) throws IOException;
}
