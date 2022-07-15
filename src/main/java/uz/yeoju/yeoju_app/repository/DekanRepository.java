package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekan.Dekan;
import uz.yeoju.yeoju_app.payload.resDto.dekan.CourseStatistics;

import java.time.LocalDateTime;
import java.util.List;

public interface DekanRepository extends JpaRepository<Dekan,String> {


    @Query(value = "select countCome.level,countCome.comeCount,allUser.allCount from\n" +
            "(select g.level,count(user_id) as comeCount from\n" +
            "   (\n" +
            "       select u.* from acc_monitor_log al\n" +
            "       join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "       join users_Role ur on u.id = ur.users_id\n" +
            "       join (select id from Role where roleName='Student') as role on role.id = ur.roles_id\n" +
            "       where al.time\n" +
            "                 between\n" +
            "                 :dateFrom\n" +
            "                 and\n" +
            "                 :dateTo\n" +
            "    ) as users1\n" +
            "join Student s on users1.id = s.user_id\n" +
            "   join groups g on g.id = s.group_id\n" +
            "       join Faculty F on F.id = g.faculty_id\n" +
            "               where g.faculty_id=:facultyId\n" +
            "group by g.level\n" +
            ") as countCome\n" +
            "join(\n" +
            "select g1.level, count(s.id) as allCount from Student s\n" +
            "join groups g1 on g1.id = s.group_id\n" +
            "join Faculty F on F.id = g1.faculty_id\n" +
            "where g1.faculty_id=:facultyId\n" +
            "group by g1.level\n" +
            ") as allUser on countCome.level=allUser.level",nativeQuery = true)
    List<CourseStatistics> getCourseStatisticsForDekan(
            @Param("facultyId") String facultyId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

}
