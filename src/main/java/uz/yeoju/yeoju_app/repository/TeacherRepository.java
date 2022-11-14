package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Teacher;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.*;

import java.util.Date;

//@EnableJpaRepositories
public interface TeacherRepository extends JpaRepository<Teacher, String> {

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


    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get30OfMonthStatistics getDaysOfMonthStatistics30(@Param("id") String id);

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get29OfMonthStatistics getDaysOfMonthStatistics29(@Param("id") String id);

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    Get28OfMonthStatistics getDaysOfMonthStatistics28(@Param("id") String id);


}
