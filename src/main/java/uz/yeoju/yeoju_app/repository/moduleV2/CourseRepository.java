package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.moduleV2.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,String> {
     List<Course> findAllByPlanId(String planId);
}
