package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.module;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TopicFileOfLineV2Repository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleImplService implements ModuleService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final TopicFileOfLineV2Repository topicFileOfLineV2Repository;

    public ModuleImplService(CourseRepository courseRepository, ModuleRepository moduleRepository, TopicFileOfLineV2Repository topicFileOfLineV2Repository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.topicFileOfLineV2Repository = topicFileOfLineV2Repository;
    }

    @Override
    @Transactional
    public void createModule(ModuleCreator creator) {
        if (courseRepository.existsById(creator.courseId)){
            Module module = new Module(creator.title, creator.theme, courseRepository.findById(creator.courseId).get());
            moduleRepository.save(module);
        } else {
            throw new UserNotFoundException("Course did not found by id: "+creator.courseId);
        }
    }

    @Override
    @Transactional
    public ApiResponse updateModule(ModuleUpdater updater) {
        if (moduleRepository.existsById(updater.id)){
            Module module = moduleRepository.findById(updater.getId()).get();
            if (updater.getTitle()!=null && !updater.getTitle().isEmpty()) {
                module.setTitle(updater.getTitle());
            }
            if (updater.theme!=null && !updater.theme.isEmpty()) {
                module.setTheme(updater.getTheme());
            }
            Module save = moduleRepository.save(module);
            return new ApiResponse(true,"Module updated",save);
        } else {
            throw new UserNotFoundException("Module did not found by id: "+updater.id);
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
            ModuleResponse moduleResponse = new ModuleResponse();
            moduleResponse.setId(id);
            Module module = moduleRepository.findById(id).get();
            moduleResponse.setTitle(module.getTitle());
            moduleResponse.setTheme(module.getTheme());

            List<TopicFileOfLineV2Dto> topicFiles = new ArrayList<>();
            List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
            boolean isTopicFilesEmpty = topics.isEmpty();
            if (!isTopicFilesEmpty&&topics.size()>0) {
                topics.forEach(tf -> {
                    topicFiles.add(new TopicFileOfLineV2Dto(
                            tf.getName(),
                            tf.getFileType(),
                            tf.getContentType(),
                            tf.getPackageName()
                    ));
                });
            }

            moduleResponse.setTopicFiles(topicFiles);
            return new ApiResponse(true,"Module is found by id: "+id,moduleResponse);
        }
        else {
            throw new UserNotFoundException("Module not found by id: "+id);
        }
    }
}
