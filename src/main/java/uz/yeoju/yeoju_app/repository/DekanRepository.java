package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekan.Dekan;
import uz.yeoju.yeoju_app.payload.resDto.dekan.CourseStatistics;
import uz.yeoju.yeoju_app.payload.resDto.dekan.DekanGroupsStatistic;

import java.time.LocalDateTime;
import java.util.List;

public interface DekanRepository extends JpaRepository<Dekan,String> {

    Dekan getDekanByUserId(String user_id);


    @Query(value = "select countCome.level,countCome.comeCount,allUser.allCount from\n" +
            "(select g.level,count(user_id) as comeCount from\n" +
            "   (\n" +
            "       select u.id from acc_monitor_log al\n" +
            "       join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "       join users_Role ur on u.id = ur.users_id\n" +
            "       join (select id from Role where roleName='Student') as role on role.id = ur.roles_id\n" +
            "       where al.time\n" +
            "                 between\n" +
            "                 :dateFrom\n" +
            "                 and\n" +
            "                 :dateTo\n" +
            "group by u.id" +
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
            ") as allUser on countCome.level=allUser.level order by countCome.level",nativeQuery = true)
    List<CourseStatistics> getCourseStatisticsForDekan(
            @Param("facultyId") String facultyId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query(value = "select g1.level,g1.name, g1.comeCount,g2.allCount from (\n" +
            "            select g.level,g.name,count(card.cardNo) as comeCount from\n" +
            "              (\n" +
            "                    select  al.card_no as cardNo\n" +
            "              from acc_monitor_log al  join users u\n" +
            "                  on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                  join users_Role ur on u.id = ur.users_id\n" +
            "                  join Role R2 on ur.roles_id = R2.id\n" +
            "              where al.time between :dateFrom and :dateTo and R2.roleName='Student'\n" +
            "              group by al.card_no    ) as card\n" +
            "                   join users u on cast(card.cardNo as varchar) =\n" +
            "                                    cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                    join Student S on u.id = S.user_id\n" +
            "                    join groups g on g.id = S.group_id\n" +
            "                    join Faculty F on F.id = g.faculty_id\n" +
            "            where F.id =:facultyId\n" +
            "            group by g.name, g.level\n" +
            "                ) as g1\n" +
            "            join (select g.name,count(s.id) as allCount from Student s\n" +
            "    join groups g on s.group_id = g.id\n" +
            "    join Faculty F on F.id = g.faculty_id\n" +
            "    and F.id = :facultyId\n" +
            "    group by g.name) as g2 on g2.name = g1.name order by g1.level,g1.name",nativeQuery = true)
    List<DekanGroupsStatistic> getGroupsStatisticForDekan(
            @Param("facultyId") String facultyId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query(value = "select g.name from groups g\n" +
            "where g.faculty_id =:facultyId\n" +
            "order by g.level desc",nativeQuery = true)
    List<String> getGroupsNamesForDekanByFacultyId(@Param("facultyId") String facultyId);
}
