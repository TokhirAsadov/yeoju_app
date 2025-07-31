package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testOptionV2;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.UserTestAnswerV2Finisher;

public interface TestOptionV2Service {
    ApiResponse finishCourseTest(UserTestAnswerV2Finisher finisher);
}
