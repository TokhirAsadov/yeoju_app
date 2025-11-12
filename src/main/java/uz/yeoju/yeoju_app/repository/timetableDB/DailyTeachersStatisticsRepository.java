package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeachersStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetKafedraTeacherStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetDailyTeacherStatistics;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DailyTeachersStatisticsRepository extends JpaRepository<DailyTeachersStatistics, String> {
    @Query(value = "select Top 1\n" +
            "    d.id,\n" +
            "    d.year,\n" +
            "    d.month,\n" +
            "    d.day,\n" +
            "    d.totalMissed as totalNotAttended,\n" +
            "    d.totalAttended\n" +
            "from DailyTeachersStatistics d\n" +
            "where d.teacher_id=?1 and d.year=?2 and d.month=?3 and d.day=?4",nativeQuery = true)
    GetDailyTeacherStatistics getDailyTeacherStatisticsByDay(String teacherId, Integer year, Integer month, Integer day);

    @Query(value = "select\n" +
            "    d.id,\n" +
            "    d.year,\n" +
            "    d.month,\n" +
            "    d.day,\n" +
            "    d.week,\n" +
            "    d.weekday,\n" +
            "    d.totalMissed as totalNotAttended,\n" +
            "    d.totalAttended\n" +
            "from DailyTeachersStatistics d\n" +
            "where d.teacher_id=?1 and d.year=?2 and d.week=?3 order by d.weekday",nativeQuery = true)
    List<GetDailyTeacherStatistics> getDailyTeacherStatisticsByWeek(String teacherId, Integer year, Integer week);

    @Query(value = "select\n" +
            "    d.id,\n" +
            "    d.year,\n" +
            "    d.month,\n" +
            "    d.day,\n" +
            "    d.week,\n" +
            "    d.weekday,\n" +
            "    d.totalMissed as totalNotAttended,\n" +
            "    d.totalAttended\n" +
            "from DailyTeachersStatistics d\n" +
            "where d.teacher_id=?1 and d.year=?2 and d.month=?3 order by d.day",nativeQuery = true)
    List<GetDailyTeacherStatistics> getDailyTeacherStatisticsByMonth(String teacherId, Integer year, Integer month);

    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    T.rate,\n" +
            "    ?2 as year,\n" +
            "    ?3 as month\n" +
            "from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =?1 and u.id in ?4",nativeQuery = true)
    List<GetKafedraTeacherStatistics> getKafedraTeacherStatistics(String kafedraId, Integer year, Integer month, Set<String> teachersIds);

    Optional<DailyTeachersStatistics> findDailyTeachersStatisticsByTeacherIdAndYearAndMonthAndWeekAndWeekdayAndDay(String teacher_id, Integer year,Integer month, Integer week, Integer weekday, Integer day);
}
