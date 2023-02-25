package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentData;
import uz.yeoju.yeoju_app.payload.resDto.fayzulla.UserShareRFID;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.KafedraStatistics;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.RektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StaffDatasForRektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StudentDatasForRektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers28;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers29;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers31;
import uz.yeoju.yeoju_app.payload.resDto.search.SearchDto;
import uz.yeoju.yeoju_app.payload.resDto.user.UserField;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSave;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;
import uz.yeoju.yeoju_app.payload.resDto.user.UserResDto;

import java.util.List;

public interface UserRFIDRepository extends JpaRepository<User, String> {

    User getUserByLogin(String login);

    User findUserByRFID(String RFID);

    @Query(value = "select u.login,u.RFID,u.fullName,P.userPositionName as position\n" +
            "from users u join users_Position uP on u.id = uP.users_id\n" +
            "join Position P on uP.positions_id = P.id where u.RFID=:rfid",nativeQuery = true)
    UserShareRFID getUserShareRFID(@Param("rfid") String rfid);

}
