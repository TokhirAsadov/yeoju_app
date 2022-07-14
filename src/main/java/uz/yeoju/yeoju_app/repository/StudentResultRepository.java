package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.studentBall.StudentResult;

import java.util.List;

public interface StudentResultRepository extends JpaRepository<StudentResult, Long> {
    List<StudentResult> getStudentResultsByUserLogin(String user_login);
}
