package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.EducationLanguage;

import java.util.Optional;

public interface EducationLanRepository extends JpaRepository<EducationLanguage, String> {
    EducationLanguage getEducationLanguageByName(String name);
    Optional<EducationLanguage> findEducationLanguageByName(String name);
    boolean existsEducationLanguageByName(String name);
}
