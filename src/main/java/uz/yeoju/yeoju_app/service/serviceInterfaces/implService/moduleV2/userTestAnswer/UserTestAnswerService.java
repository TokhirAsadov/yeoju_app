package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.TestResultDto;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerFinisher;

public interface UserTestAnswerService {
    ApiResponse finishCourseTest(UserTestAnswerFinisher finisher);
    ApiResponse create(UserTestAnswerCreator creator);

}
