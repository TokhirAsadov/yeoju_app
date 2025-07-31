package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.userTestAnswerV2;

import uz.yeoju.yeoju_app.payload.moduleV2.TestResultDto;

public interface UserTestAnswerV2Service {
    TestResultDto calculateUserScore(String studentUserId, String testId);
}
