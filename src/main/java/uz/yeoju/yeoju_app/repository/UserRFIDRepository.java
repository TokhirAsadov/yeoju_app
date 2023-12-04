package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.resDto.fayzulla.UserShareRFID;

public interface UserRFIDRepository extends JpaRepository<User, String> {

    User getUserByLogin(String login);

    User findUserByRFID(String RFID);

    @Query(value = "select u.login,u.RFID,u.fullName,P.userPositionName as position\n" +
            "from users u join users_Position uP on u.id = uP.users_id\n" +
            "join Position P on uP.positions_id = P.id where u.RFID=:rfid",nativeQuery = true)
    UserShareRFID getUserShareRFID(@Param("rfid") String rfid);

}
