package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.UserLessonModuleProgress;

import java.util.List;

public interface UserLessonModuleProgressRepository extends JpaRepository<UserLessonModuleProgress,String> {
    boolean existsByUserAndModuleAndCompletedTrue(User user, Module module);

    List<UserLessonModuleProgress> findAllByModule(Module module);


}
