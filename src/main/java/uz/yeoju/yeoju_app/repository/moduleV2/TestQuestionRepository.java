package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;

import java.util.List;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,String> {


}
