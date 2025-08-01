package uz.yeoju.yeoju_app.repository.moduleV2.testV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;

import java.sql.Timestamp;
import java.util.List;

public interface UserTestAnswerV2Repository extends JpaRepository<UserTestAnswerV2,String> {


}
