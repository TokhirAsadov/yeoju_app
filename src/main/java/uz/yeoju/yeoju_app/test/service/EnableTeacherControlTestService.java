package uz.yeoju.yeoju_app.test.service;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.test.payload.CreatorEnableTeacherControlTestDto;

public interface EnableTeacherControlTestService {
    ApiResponse findAllEnableTeacherByKafedraId(String kafkaId);
    ApiResponse getAllTeacherLessonsByTeacherId(String teacherId);

    ApiResponse createAndUpdateEnableTeacher(CreatorEnableTeacherControlTestDto dto);
}
