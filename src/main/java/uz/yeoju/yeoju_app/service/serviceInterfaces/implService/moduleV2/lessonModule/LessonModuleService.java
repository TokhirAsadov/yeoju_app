package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.lessonModule;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.LessonModuleCreator;

public interface LessonModuleService {
    void createLesson(LessonModuleCreator creator);
    boolean deleteLesson(String lessonModuleId);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);
}
