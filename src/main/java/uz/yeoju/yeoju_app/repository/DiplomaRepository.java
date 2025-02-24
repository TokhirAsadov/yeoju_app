package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.dekanat.Diploma;

public interface DiplomaRepository extends JpaRepository<Diploma, String> {
    Boolean existsDiplomaByLogin(String login);
    Boolean existsDiplomaByDiplomId(String diplomId);
    Boolean existsDiplomaByDiplomRaqami(String diplomRaqami);
}
