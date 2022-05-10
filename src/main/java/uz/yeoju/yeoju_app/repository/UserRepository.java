package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserByLoginOrEmailOrRFID(String login, String email, String RFID);
    User getUserByLogin(String login);
    User getUserByEmail(String email);
    User getUserByRFID(String RFID);
    boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID);

}
