package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Faculty;

import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {
    Faculty getFacultyByName(String name);
    boolean existsFacultyByName(String name);
}
