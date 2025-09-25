package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.timetableDB.KafedraTeachersStatistics;

import java.time.LocalDate;
import java.util.List;

public interface KafedraTeachersStatisticsRepository extends JpaRepository<KafedraTeachersStatistics, String> {
    @Query(value = "SELECT * " +
            "    FROM KafedraTeachersStatistics kts\n" +
            "    WHERE CONVERT(date, kts.day, 104)  -- 104 = 'dd.mm.yyyy' format\n" +
            "          BETWEEN :weekStart AND :weekEnd", nativeQuery = true)
    List<KafedraTeachersStatistics> findByWeekRange(
            @Param("weekStart") LocalDate weekStart,
            @Param("weekEnd")   LocalDate weekEnd);

}
