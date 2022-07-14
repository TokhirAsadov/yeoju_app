package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.studentBall.Retake;

import java.util.List;

public interface RetakeRepository extends JpaRepository<Retake, Long> {
    List<Retake> getRetakesByUserId(String user_id);
}
