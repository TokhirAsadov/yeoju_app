package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.timetableDB.KafedraTeachersStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetAllKafedraTeachersStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraStatistics;

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

    @Query(value = ";WITH WeekDays AS (\n" +
            "                SELECT \n" +
            "                    StartDate = DATEADD(\n" +
            "                                   WEEK,\n" +
            "                                   :week - 1,\n" +
            "                                   DATEADD(WEEK, DATEDIFF(WEEK, 0, DATEFROMPARTS(:year,1,4)), 0)\n" +
            "                               )\n" +
            "            )\n" +
            "            SELECT FORMAT(DATEADD(DAY, v.number, StartDate), 'dd.MM.yyyy') AS dayInWeek\n" +
            "            FROM WeekDays\n" +
            "            JOIN master..spt_values v\n" +
            "                 ON v.type = 'P' AND v.number BETWEEN 0 AND 5\n" +
            "            ORDER BY dayInWeek\n" +
            "            ", nativeQuery = true)
    List<GetAllKafedraTeachersStatistics> getDaysOfWeek(@Param("year") int year, @Param("week") int week);


    @Query(value = "SELECT kts.id, kts.totalAttended, kts.totalMissed, k.id as kafedraId, k.nameEn as kafedraName\n" +
            "FROM KafedraTeachersStatistics kts\n" +
            "join Kafedra k on kts.kafedra_id = k.id\n" +
            "where kts.day=:day;",nativeQuery = true)
    List<GetKafedraStatistics> findStatisticsByDay(String day);
}
