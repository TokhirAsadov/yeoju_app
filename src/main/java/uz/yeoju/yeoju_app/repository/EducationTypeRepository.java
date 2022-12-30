package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.EducationType;

import java.util.Optional;

public interface EducationTypeRepository extends JpaRepository<EducationType, String> {
    EducationType getEducationTypeByName(String name);

    Optional<EducationType> findEducationTypeByName(String name);
    boolean existsEducationTypeByName(String name);
}
