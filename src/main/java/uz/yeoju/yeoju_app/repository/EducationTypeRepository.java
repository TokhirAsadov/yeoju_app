package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.EducationType;

public interface EducationTypeRepository extends JpaRepository<EducationType, String> {
    EducationType getEducationTypeByName(String name);
    boolean existsEducationTypeByName(String name);
}
