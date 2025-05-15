package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.module;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleCreator;

public interface ModuleService {
    void createModule(ModuleCreator creator);
    boolean deleteModule(String courseId);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);
}
