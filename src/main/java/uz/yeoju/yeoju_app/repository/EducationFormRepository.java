package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.EducationForm;

public interface EducationFormRepository extends JpaRepository<EducationForm, String> {
    EducationForm getEducationFormByName(String name);
    boolean existsEducationFormByName(String name);
}
