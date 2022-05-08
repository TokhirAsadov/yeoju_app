package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User getByLogin(String login);
}
