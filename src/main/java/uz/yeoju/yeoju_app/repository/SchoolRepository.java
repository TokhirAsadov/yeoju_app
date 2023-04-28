package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.school.School;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School,String> {
    Optional<School> findSchoolByCode(Integer code);
    Boolean existsSchoolByCode(Integer code);

    Optional<School> findSchoolByNameEnOrNameRuOrNameUz(String nameEn, String nameRu, String nameUz);
    Boolean existsSchoolByNameEnOrNameRuOrNameUz(String nameEn, String nameRu, String nameUz);
}
