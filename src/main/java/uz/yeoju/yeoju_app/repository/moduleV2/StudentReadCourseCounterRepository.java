package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.StudentReadCourseCounter;
public interface StudentReadCourseCounterRepository extends JpaRepository<StudentReadCourseCounter,String> {
    @Modifying
    @Transactional
    @Query(value = "EXEC IncreaseStudentReadCounter @userId = ?1, @courseId = ?2", nativeQuery = true)
    void increaseStudentReadCounter(String userId, String courseId);

}
