package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.attachment.Obyektivka;

import java.util.List;

public interface ObyektivkaRepo extends JpaRepository<Obyektivka, String> {
    List<Obyektivka> findObyektivkasByUserId(String user_id);
    List<Obyektivka> findObyektivkasByActive(boolean active);
    Obyektivka findObyektivkaByActive(boolean active);
    boolean existsObyektivkaByUserId(String user_id);
    boolean existsObyektivkaByActive(boolean active);
}
