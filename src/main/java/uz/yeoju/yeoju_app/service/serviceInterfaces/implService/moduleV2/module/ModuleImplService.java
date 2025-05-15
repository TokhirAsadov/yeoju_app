package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.module;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleCreator;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;

@Service
public class ModuleImplService implements ModuleService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    public ModuleImplService(CourseRepository courseRepository, ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    @Transactional
    public void createModule(ModuleCreator creator) {
        if (courseRepository.existsById(creator.courseId)){
            Module module = new Module(creator.title, courseRepository.findById(creator.courseId).get());
            moduleRepository.save(module);
        } else {
            throw new UserNotFoundException("Course did not found by id: "+creator.courseId);
        }
    }

    @Override
    public boolean deleteModule(String moduleId) {
        if(moduleRepository.existsById(moduleId)){
            moduleRepository.deleteById(moduleId);
            return true;
        } else {
            throw new UserNotFoundException("Module did not found by id: "+moduleId);
        }
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Modules",moduleRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        if (moduleRepository.existsById(id)) {
            return new ApiResponse(true,"Module is found by id: "+id,moduleRepository.findById(id).get());
        }
        else {
            throw new UserNotFoundException("Module not found by id: "+id);
        }
    }
}
