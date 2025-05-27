package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;

public interface ModuleRepository extends JpaRepository<Module,String> {
    boolean existsByIdAndModuleTestIdIsNotNull(String moduleId);
}
