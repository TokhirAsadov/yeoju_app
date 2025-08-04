package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userLessonModuleProgress;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.UserLessonModuleProgressCreator;

public interface UserLessonModuleProgressService {
    void create(UserLessonModuleProgressCreator creator);
    void createV2(UserLessonModuleProgressCreator creator);
    boolean delete(String id);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);
}
