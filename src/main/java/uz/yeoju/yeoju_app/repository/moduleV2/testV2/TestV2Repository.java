package uz.yeoju.yeoju_app.repository.moduleV2.testV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestV2;

public interface TestV2Repository extends JpaRepository<TestV2,String> {
    boolean existsByCourseId(String courseId);
    TestV2 findByCourseId(String id);
    TestV2 findByModuleId(String id);
}
