package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userLessonModuleProgress;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.moduleV2.UserLessonModuleProgress;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.UserLessonModuleProgressCreator;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.LessonModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserLessonModuleProgressRepository;

@Service
public class UserLessonModuleProgressImplService implements UserLessonModuleProgressService{
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;
    private final LessonModuleRepository lessonModuleRepository;
    private final UserRepository userRepository;

    public UserLessonModuleProgressImplService(UserLessonModuleProgressRepository userLessonModuleProgressRepository, LessonModuleRepository lessonModuleRepository, UserRepository userRepository) {
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.lessonModuleRepository = lessonModuleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(UserLessonModuleProgressCreator creator) {
        if (userRepository.existsById(creator.userId)) {
            if (lessonModuleRepository.existsById(creator.lessonModuleId)){
                UserLessonModuleProgress userLessonModuleProgress = new UserLessonModuleProgress(
                        userRepository.findById(creator.userId).get(),
                        lessonModuleRepository.findById(creator.lessonModuleId).get(),
                        false
                );
                userLessonModuleProgressRepository.save(userLessonModuleProgress);
            }
            throw new UserNotFoundException("Lesson module not found by id: "+creator.lessonModuleId);
        }
        throw new UserNotFoundException("User not found by id: "+creator.userId);
    }

    @Override
    public boolean delete(String id) {
        if(userLessonModuleProgressRepository.existsById(id)){
            userLessonModuleProgressRepository.deleteById(id);
            return true;
        } else {
            throw new UserNotFoundException("User lesson module progress did not found by id: "+id);
        }
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"User lesson module progress",userLessonModuleProgressRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        if (userLessonModuleProgressRepository.existsById(id)) {
            return new ApiResponse(true,"User lesson module progress is found by id: "+id,userLessonModuleProgressRepository.findById(id).get());
        }
        else {
            throw new UserNotFoundException("User lesson module progress not found by id: "+id);
        }
    }
}
