package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Gander;
import uz.yeoju.yeoju_app.entity.enums.GanderName;

public interface GanderRepository extends JpaRepository<Gander,Long> {
    Gander getGanderByGanderName(GanderName ganderName);
    boolean existsGanderByGanderName(GanderName ganderName);
}
