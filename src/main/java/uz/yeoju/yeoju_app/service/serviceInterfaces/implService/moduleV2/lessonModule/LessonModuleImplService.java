package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.lessonModule;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.LessonModule;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.LessonModuleCreator;
import uz.yeoju.yeoju_app.repository.moduleV2.LessonModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;

@Service
public class LessonModuleImplService implements LessonModuleService {
    private final LessonModuleRepository lessonModuleRepository;
    private final ModuleRepository moduleRepository;

    public LessonModuleImplService(LessonModuleRepository lessonModuleRepository, ModuleRepository moduleRepository) {
        this.lessonModuleRepository = lessonModuleRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    @Transactional
    public void createLesson(LessonModuleCreator creator) {
        if (moduleRepository.existsById(creator.moduleId)){
            LessonModule lessonModule = new LessonModule(creator.title, creator.theme, moduleRepository.findById(creator.moduleId).get());
            lessonModuleRepository.save(lessonModule);
        } else {
            throw new UserNotFoundException("Module did not found by id: "+creator.moduleId);
        }
    }

    @Override
    public boolean deleteLesson(String lessonModuleId) {
        if(lessonModuleRepository.existsById(lessonModuleId)){
            lessonModuleRepository.deleteById(lessonModuleId);
            return true;
        } else {
            throw new UserNotFoundException("Lesson module did not found by id: "+lessonModuleId);
        }
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Lesson modules",lessonModuleRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        if (lessonModuleRepository.existsById(id)) {
            return new ApiResponse(true,"Lesson module is found by id: "+id,lessonModuleRepository.findById(id).get());
        }
        else {
            throw new UserNotFoundException("Lesson module not found by id: "+id);
        }
    }
}
