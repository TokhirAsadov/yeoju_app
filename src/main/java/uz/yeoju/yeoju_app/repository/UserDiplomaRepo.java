package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.attachment.UserDiploma;

import java.util.List;

public interface UserDiplomaRepo extends JpaRepository<UserDiploma, String> {
    List<UserDiploma> findUserDiplomasByUserId(String user_id);
    List<UserDiploma> findUserDiplomasByActive(boolean active);
    UserDiploma findUserDiplomaByActive(boolean active);
    boolean existsUserDiplomaByUserId(String user_id);
    boolean existsUserDiplomaByActive(boolean active);
}
