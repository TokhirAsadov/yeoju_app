package uz.yeoju.yeoju_app.repository.moduleV2.testV2;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestOptionV2;

public interface TestOptionV2Repository extends JpaRepository<TestOptionV2,String> {
    void deleteAllByQuestionV2Id(String id);
}
