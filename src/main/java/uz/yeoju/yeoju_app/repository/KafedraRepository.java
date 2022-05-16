package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Kafedra;

public interface KafedraRepository extends JpaRepository<Kafedra, String> {
    Kafedra getKafedraByName(String name);
    boolean existsKafedraByName(String name);
}
