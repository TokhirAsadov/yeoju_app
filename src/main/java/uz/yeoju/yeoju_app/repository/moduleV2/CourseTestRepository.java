package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.moduleV2.CourseTest;

public interface CourseTestRepository extends JpaRepository<CourseTest,String> {
    boolean existsByCourseId(String course_id);
}
