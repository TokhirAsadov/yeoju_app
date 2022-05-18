package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.ProfissorPedagog;

import java.sql.Timestamp;
import java.util.List;

public interface ProfissorPedagogRepository extends JpaRepository<ProfissorPedagog, String> {
    ProfissorPedagog findProfissorPedagogByUserId(String user_id);
    List<ProfissorPedagog> findStudentsByBornYear(Timestamp bornYear);
    List<ProfissorPedagog> findProfissorPedagogsByEnrollmentYear(Timestamp enrollmentYear);
    List<ProfissorPedagog> findProfissorPedagogsByCitizenship(String citizenship);
    boolean existsProfissorPedagogByUserId(String user_id);
}
