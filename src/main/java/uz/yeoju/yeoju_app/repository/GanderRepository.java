package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Gander;
import uz.yeoju.yeoju_app.entity.enums.Gandername;

public interface GanderRepository extends JpaRepository<Gander,Long> {
    Gander getGanderByGandername(Gandername gandername);
    boolean existsGanderByGandername(Gandername gandername);
}
