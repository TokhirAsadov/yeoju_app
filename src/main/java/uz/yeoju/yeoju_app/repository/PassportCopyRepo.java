package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.attachment.PassportCopy;

import java.util.List;

public interface PassportCopyRepo extends JpaRepository<PassportCopy, String> {
//    @Query("select p from PassportCopy p where p.usersss.id = ?1")
    List<PassportCopy> findPassportCopiesByUserId(String user_id);
    List<PassportCopy> findPassportCopiesByActive(boolean active);
    PassportCopy findPassportCopyByActive(boolean active);
    boolean existsPassportCopyByUserId(String user_id);
    boolean existsPassportCopyByActive(boolean active);
}
