package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.timetableDB.KafedraTeachersStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetAllKafedraTeachersStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetKafedraTeachersStatistics;

import java.util.List;

public interface KafedraTeachersStatisticsRepository extends JpaRepository<KafedraTeachersStatistics, String> {
    @Query(value = "SELECT sum( kts.totalAttended) as totalAttended, sum(kts.totalMissed) as totalMissed, k.id as kafedraId, k.nameEn as kafedraName\n" +
            "FROM KafedraTeachersStatistics kts\n" +
            "    join Kafedra k on k.id=kts.kafedra_id\n" +
            "WHERE kts.day between :weekStart and :weekEnd\n" +
            "group by k.id, k.nameEn order by k.nameEn;", nativeQuery = true)
    List<GetKafedraStatistics> findByWeekRange(
            @Param("weekStart") String weekStart,
            @Param("weekEnd")   String weekEnd);

    @Query(value = "SELECT Top 1 sum( kts.totalAttended) as totalAttended, sum(kts.totalMissed) as totalMissed, k.id as kafedraId, k.nameEn as kafedraName\n" +
            "FROM KafedraTeachersStatistics kts\n" +
            "    join Kafedra k on k.id=kts.kafedra_id\n" +
            "WHERE kts.day between :weekStart and :weekEnd and k.id=:kafedraId\n" +
            "group by k.id, k.nameEn order by k.nameEn;", nativeQuery = true)
    GetKafedraStatistics findByWeekRange(
            @Param("kafedraId") String kafedraId,
            @Param("weekStart") String weekStart,
            @Param("weekEnd")   String weekEnd);

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


    @Query(value = "WITH WeekDays AS (\n" +
            "    SELECT StartDate = DATEADD(\n" +
            "                           WEEK,\n" +
            "                           :week - 1,\n" +
            "                           DATEADD(WEEK, DATEDIFF(WEEK, 0, DATEFROMPARTS(:year,1,4)), 0)\n" +
            "                       )\n" +
            ")\n" +
            "SELECT FORMAT(DATEADD(DAY, v.number, StartDate), 'dd.MM.yyyy') AS dayInWeek,\n" +
            "       CAST(:kafedraId AS uniqueidentifier) AS kafedraId\n" +
            "FROM WeekDays\n" +
            "JOIN master..spt_values v\n" +
            "     ON v.type = 'P' AND v.number BETWEEN 0 AND 5\n" +
            "ORDER BY dayInWeek", nativeQuery = true)
    List<GetKafedraTeachersStatistics> getDaysOfWeek2(@Param("year") int year, @Param("week") int week, @Param("kafedraId") String kafedraId);


    @Query(value = "SELECT kts.id, kts.totalAttended, kts.totalMissed, k.id as kafedraId, k.nameEn as kafedraName \n" +
            "FROM KafedraTeachersStatistics kts\n" +
            "join Kafedra k on kts.kafedra_id = k.id\n" +
            "where kts.day=:day order by k.nameEn ;",nativeQuery = true)
    List<GetKafedraStatistics> findStatisticsByDay(@Param("day") String day);


    @Query(value = "SELECT TOP 1 kts.id, kts.totalAttended, kts.totalMissed, k.id as kafedraId, k.nameEn as kafedraName \n" +
            "FROM KafedraTeachersStatistics kts \n" +
            "join Kafedra k on kts.kafedra_id = k.id \n" +
            "where kts.day=:day and k.id=:kafedraId",nativeQuery = true)
    GetKafedraStatistics findStatisticsByKafedraIdAndDay(@Param("kafedraId") String kafedraId, @Param("day") String day);
}
