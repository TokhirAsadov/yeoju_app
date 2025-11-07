package uz.yeoju.yeoju_app.repository.timetableDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeachersStatistics;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetDailyTeacherStatistics;

import java.util.List;

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
}
