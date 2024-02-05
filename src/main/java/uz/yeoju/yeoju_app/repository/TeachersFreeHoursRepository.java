package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.teacher.TeachersFreeHours;

import java.util.Optional;

public interface TeachersFreeHoursRepository extends JpaRepository<TeachersFreeHours,String> {
    Optional<TeachersFreeHours> findByIdAndCreatedBy(String id, String createdBy);
}
