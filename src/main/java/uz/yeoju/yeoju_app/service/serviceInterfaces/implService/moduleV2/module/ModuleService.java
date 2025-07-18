package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.module;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleUpdater;

public interface ModuleService {
    void createModule(ModuleCreator creator);
    ApiResponse updateModule(ModuleUpdater updater);
    boolean deleteModule(String moduleId);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);

    void uploadModuleFile(String moduleId,MultipartFile file);
}
