package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByLogin(String login);
    User getUserByEmail(String email);
    User getUserByRFID(String RFID);
    boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID);

}
