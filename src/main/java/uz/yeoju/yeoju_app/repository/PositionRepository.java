package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position getPositionByUserPositionName(String name);
    boolean existsPositionByUserPositionName(String name);
}
