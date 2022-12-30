package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Teacher;
import uz.yeoju.yeoju_app.payload.resDto.TeacherCountComeAndAll;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.*;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.TeacherStatusAndPosition;

import java.util.Date;
import java.util.List;

//@EnableJpaRepositories
public interface TeacherRepository extends JpaRepository<Teacher, String> {



    @Query(value = "select f2.kafedra_id as id, f1.count as comeCount,f2.count as allCount from (\n" +
            "        select t.kafedra_id,count(card.cardNo) as count from\n" +
            "            (select  al.card_no as cardNo\n" +
            "             from acc_monitor_log al\n" +
            "                      join users u\n" +
            "                           on cast(u.RFID as varchar) =\n" +
            "                              cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                      join users_Role ur\n" +
            "                           on u.id = ur.users_id\n" +
            "                      join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                           on ur.roles_id = role.id\n" +
            "             where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "             group by al.card_no\n" +
            "            ) as card\n" +
            "                join users u on cast(card.cardNo as varchar) =\n" +
            "                                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join Teacher t on u.id = t.user_id\n" +
            "        group by t.kafedra_id\n" +
            "    ) as f1\n" +
            "    right join (\n" +
            "    select t.kafedra_id,count(t.id) as count from Teacher t\n" +
            "    group by t.kafedra_id\n" +
            ") as f2 on f2.kafedra_id = f1.kafedra_id",nativeQuery = true)
    List<TeacherCountComeAndAll> getCountComeAndAll();

    @Query(value = "select  count(al.card_no) as count\n" +
            "from acc_monitor_log al join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between DATEADD(d, 0, getdate()) and DATEADD(d, 1, getdate()) and u.id=:id\n" +
            "group by al.card_no,u.fullName",nativeQuery = true)
    Integer getCountTouchOfTeacher(@Param("id") String id);


    @Query(value = "select k.nameEn from Kafedra k join Teacher t on t.kafedra_id = k.id where t.user_id=:userId",nativeQuery = true)
    String getFacultyNameByUserId(@Param("userId") String userId);

    @Query(value = "\n" +
            "select t1.cardNo,t1.timeAsc,t2.timeDesc," +
            " dateadd(d,0, CAST(CAST(YEAR(GETDATE()) AS VARCHAR(4)) + '/' + CAST(MONTH(GETDATE()) AS VARCHAR(2)) + :day AS DATETIME)) as time" +
            " from (\n" +
            "select  Top 1 al.card_no as cardNo, al.time as timeAsc\n" +
            "from acc_monitor_log al\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time \n" +
            "    between \n" +
            "        dateadd(d,0, CAST(CAST(YEAR(GETDATE()) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(GETDATE()) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "    and \n" +
            "        dateadd(d,1, CAST(CAST(YEAR(GETDATE()) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(GETDATE()) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "    and u.id=:id\n" +
            "order by al.time asc) as t1\n" +
            "join \n" +
            "    (\n" +
            "select  Top 1 al.card_no, al.time as timeDesc\n" +
            "from acc_monitor_log al join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time\n" +
            "      between\n" +
            "          dateadd(d,0, CAST(CAST(YEAR(GETDATE()) AS VARCHAR(4))\n" +
            "              + '/' + CAST(MONTH(GETDATE()) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "      and\n" +
            "          dateadd(d,1, CAST(CAST(YEAR(GETDATE()) AS VARCHAR(4))\n" +
            "              + '/' + CAST(MONTH(GETDATE()) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "    and u.id=:id\n" +
            "order by al.time desc\n" +
            ") as t2 on t1.cardNo = t2.card_no\n",nativeQuery = true)
    GetEnterOutTimes getEnterOutTimes(@Param("id") String id, @Param("day") String day);


    @Query(value = "\n" +
            "select t1.cardNo,t1.timeAsc,t2.timeDesc" +
            " from (\n" +
            "select  Top 1 al.card_no as cardNo, al.time as timeAsc\n" +
            "from acc_monitor_log al\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time \n" +
            "    between \n" +
            "        dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "    and \n" +
            "        dateadd(d,1, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "    and u.id=:id\n" +
            "order by al.time asc) as t1\n" +
            "join \n" +
            "    (\n" +
            "select  Top 1 al.card_no, al.time as timeDesc\n" +
            "from acc_monitor_log al join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time\n" +
            "      between\n" +
            "          dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "              + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "      and\n" +
            "          dateadd(d,1, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "              + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + :day AS DATETIME))\n" +
            "    and u.id=:id\n" +
            "order by al.time desc\n" +
            ") as t2 on t1.cardNo = t2.card_no\n",nativeQuery = true)
    GetEnterOutTimes getEnterOutTimes(@Param("id") String id, @Param("date") Date date, @Param("day") String day);


    @Query(value = "select t1.cardNo,t1.timeAsc,t2.timeDesc, dateadd(d,:day-1, getdate()) as time\n" +
            "from (\n" +
            "         select  Top 1 al.card_no as cardNo, al.time as timeAsc\n" +
            "         from acc_monitor_log al\n" +
            "                  join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         where al.time\n" +
            "             between\n" +
            "             dateadd(d,:day-1, getdate())\n" +
            "             and\n" +
            "             dateadd(d,:day, getdate())\n" +
            "           and u.id=:id\n" +
            "         order by al.time asc) as t1\n" +
            "         join\n" +
            "     (\n" +
            "         select  Top 1 al.card_no, al.time as timeDesc\n" +
            "         from acc_monitor_log al join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         where al.time\n" +
            "             between\n" +
            "             dateadd(d,:day-1, getdate())\n" +
            "             and\n" +
            "             dateadd(d,:day, getdate())\n" +
            "           and u.id=:id\n" +
            "         order by al.time desc\n" +
            "     ) as t2 on t1.cardNo = t2.card_no",nativeQuery = true)
    GetEnterOutTimes getEnterOutTimesForBadStudents(@Param("id") String id,@Param("day") Integer day);

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get31OfMonthStatistics getDaysOfMonthStatistics(@Param("id") String id);

    @Query(value = "select id,:date as date from users where id=:id",nativeQuery = true)
    GetDate31 getDaysOfMonthForMonthlyStatistics31(@Param("id") String id,@Param("date") Date date);
    @Query(value = "select id,:date as date from users where id=:id",nativeQuery = true)
    GetDate30 getDaysOfMonthForMonthlyStatistics30(@Param("id") String id,@Param("date") Date date);
    @Query(value = "select id,:date as date from users where id=:id",nativeQuery = true)
    GetDate29 getDaysOfMonthForMonthlyStatistics29(@Param("id") String id,@Param("date") Date date);
    @Query(value = "select id,:date as date from users where id=:id",nativeQuery = true)
    GetDate28 getDaysOfMonthForMonthlyStatistics28(@Param("id") String id,@Param("date") Date date);


    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get30OfMonthStatistics getDaysOfMonthStatistics30(@Param("id") String id);

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get29OfMonthStatistics getDaysOfMonthStatistics29(@Param("id") String id);

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get28OfMonthStatistics getDaysOfMonthStatistics28(@Param("id") String id);


    @Query(value = "select count(t.id) as count,workerStatus as name from Teacher t\n" +
            "group by workerStatus",nativeQuery = true)
    List<TeacherStatusAndPosition> getCountWorkerStatus();

    @Query(value = "select count(u.id) as count, P.userPositionName as name from Teacher t\n" +
            "join users u on t.user_id = u.id\n" +
            "join users_Position uP on u.id = uP.users_id\n" +
            "join Position P on uP.positions_id = P.id\n" +
            "group by P.userPositionName",nativeQuery = true)
    List<TeacherStatusAndPosition> getCountTeachersPosition();
}
