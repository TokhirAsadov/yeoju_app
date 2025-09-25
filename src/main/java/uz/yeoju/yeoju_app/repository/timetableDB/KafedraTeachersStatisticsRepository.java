package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.timetableDB.KafedraTeachersStatistics;

import java.time.LocalDate;
import java.util.List;

public interface KafedraTeachersStatisticsRepository extends JpaRepository<KafedraTeachersStatistics, String> {


}
