package uz.yeoju.yeoju_app.test.service;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface EnableTeacherControlTestService {
    ApiResponse findAllEnableTeacherByKafedraId(String kafkaId);
}
