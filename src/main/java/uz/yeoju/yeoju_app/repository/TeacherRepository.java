package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.payload.resDto.TeacherCountComeAndAll;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.*;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.TeacherStatusAndPosition;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.TeacherStatusAndPosition2;
import uz.yeoju.yeoju_app.payload.resDto.uquvbulim.DataOfLeaders;

import java.util.Date;
import java.util.List;

//@EnableJpaRepositories
public interface TeacherRepository extends JpaRepository<Teacher, String> {


    Boolean existsTeacherByUserId(String user_id);

    Teacher getTeacherByUserId(String user_id);

    @Query(value = "select TOP 1\n" +
            "    (\n" +
            "        select TOP 1 u.fullName from users u\n" +
            "         join users_Role uR on u.id = uR.users_id\n" +
            "         join Role r on uR.roles_id = r.id\n" +
            "        where r.roleName='Boshqarma boshlig`i'\n" +
            "    ) as headOfAcademicAffair,\n" +
            "    (\n" +
            "        select top 1 u2.fullName from users u\n" +
            "          join Teacher t on u.id = t.user_id\n" +
            "          join Kafedra k on k.id=t.kafedra_id\n" +
            "          join KafedraMudiri KM on k.id = KM.kafedra_id\n" +
            "          join users u2 on KM.user_id=u2.id\n" +
            "        where u.id=?1\n" +
            "    )as headOfDepartment,\n" +
            "    (\n" +
            "        select top 1 u3.fullName from groups g\n" +
            "                 join Faculty F on g.faculty_id = F.id\n" +
            "                 join Dekanat_Faculty df on df.faculties_id=F.id\n" +
            "                 join Dekanat d on d.id = df.Dekanat_id\n" +
            "                 join users u3 on d.owner_id=u3.id\n" +
            "                 where g.name=?2\n" +
            "        )as courseLeader,\n" +
            "    (\n" +
            "        select top 1 g.level from groups g\n" +
            "          join Faculty F on g.faculty_id = F.id\n" +
            "        where g.name=?2\n" +
            "        ) as course,\n" +
            "    (\n" +
            "        select top 1 F.name from groups g\n" +
            "          join Faculty F on g.faculty_id = F.id\n" +
            "        where g.name=?2\n" +
            "        ) as direction",nativeQuery = true)
    DataOfLeaders getDataOfLeader(String teacherId, String groupName);


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
            "select  Top 1 u.RFID as cardNo, al.time as timeAsc\n" +
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
            "select  Top 1 u.RFID as cardNo, al.time as timeDesc\n" +
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
            ") as t2 on t1.cardNo = t2.cardNo\n",nativeQuery = true)
    GetEnterOutTimes getEnterOutTimes(@Param("id") String id, @Param("date") Date date, @Param("day") String day);


    @Query(value = "SELECT *\n" +
            "FROM dbo.GetCardTimeInfo(?2, ?3, ?1)",nativeQuery = true)
    GetEnterOutTimes getEnterOutTimesNEW( String id,  Date date,String day);


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

    @Query(value = "select P.id, count(u.id) as count, P.userPositionName as name from Teacher t\n" +
            " join users u on t.user_id = u.id join users_Position uP on u.id = uP.users_id join Position P on uP.positions_id = P.id\n" +
            "where P.degree between 1042 and 1046 group by P.userPositionName,P.degree,P.id order by P.degree",nativeQuery = true)
    List<TeacherStatusAndPosition2> getCountTeachersPosition();


    @Query(value = "select count(t.id) as count,workerStatus as name from Teacher t join users u on t.user_id = u.id join users_Position uP on u.id = uP.users_id\n" +
            "where uP.positions_id=:id group by workerStatus",nativeQuery = true)
    List<TeacherStatusAndPosition> getCountTeachersPositionWithWorkerStatus(@Param("id") Long id);

    @Query(value = "select Top 1 workerStatus from Teacher where user_id=:id",nativeQuery = true)
    String getWorkerStatus(@Param("id") String id);

    @Query(value = "select count(f.count) as count2 from (select count(al.card_no) as count from acc_monitor_log al join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS join users_Position uP on u.id = uP.users_id join Position P on uP.positions_id = P.id where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and P.userPositionName = :position group by al.card_no, P.userPositionName) as f",nativeQuery = true)
    Integer getComeCountTeachersPositionByPositionName(@Param("position") String position);


//todo---------  -- 1 uqituvchilarning kirgan xonalarini olish --
//select  u.id,u.fullName,card.room from
//    (select  u.RFID as cardNo, ad.door_name as room
//     from acc_monitor_log al
//              join acc_door ad on ad.device_id=al.device_id
//              join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS
//     where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)
//    ) as card
//      right join users u on card.cardNo = u.RFID
//where u.id=:userId
//group by u.id, u.fullName, card.room

//    @Query(value = "select  u.id,u.fullName,card.room from\n" +
//            "    (select  u.RFID as cardNo, ad.door_name as room\n" +
//            "     from acc_monitor_log al\n" +
//            "              join acc_door ad on ad.device_id=al.device_id\n" +
//            "              join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "     where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
//            "    ) as card\n" +
//            "      right join users u on card.cardNo = u.RFID\n" +
//            "where u.id=:userId\n" +
//            "group by u.id, u.fullName, card.room",nativeQuery = true)
//    UserCheckRoomStatistics getUserCheckRoomStatistics(@Param("userId") String userId);


//todo-------------------------------------------
//    @Query(value = "select  u.id,u.fullName,ad.door_name as room,\n" +
//            "          al.time,\n" +
//            "        CASE\n" +
//            "            WHEN\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    9,\n" +
//            "                    00,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 1\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    9,\n" +
//            "                    00,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    9,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 1\n" +
//            "\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    9,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    10,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 2\n" +
//            "\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    10,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    11,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 3\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    11,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    12,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 4\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    12,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    13,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 5\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    13,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    14,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 6\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    14,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    15,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 7\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    15,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    16,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 8\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    16,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    17,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 9\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    17,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    18,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 10\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    18,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    19,\n" +
//            "                    52,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 11\n" +
//            "\n" +
//            "            when\n" +
//            "                al.time > DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    19,\n" +
//            "                    50,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )\n" +
//            "                and\n" +
//            "                al.time < DATETIMEFROMPARTS(\n" +
//            "                    DATEPART(YEAR, getdate()),\n" +
//            "                    DATEPART(MONTH, getdate()),\n" +
//            "                    DATEPART(DAY, getdate()),\n" +
//            "                    21,\n" +
//            "                    00,\n" +
//            "                    00,\n" +
//            "                    0\n" +
//            "                )  THEN 12\n" +
//            "\n" +
//            "            ELSE 0\n" +
//            "            END as section\n" +
//            "\n" +
//            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
//            "      join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "     where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and ad.door_name=:door and u.id=:userId",nativeQuery = true)
//    yangi obyekt yaratiwing kk
}
