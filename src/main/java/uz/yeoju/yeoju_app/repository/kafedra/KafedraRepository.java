package uz.yeoju.yeoju_app.repository.kafedra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.payload.resDto.dekan.FacultiesResDto;
import uz.yeoju.yeoju_app.payload.resDto.dekan.SearchUserForDekanUseSendMessage;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.*;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.*;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.TeacherComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.monthly.*;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface KafedraRepository extends JpaRepository<Kafedra, String> {
    Kafedra getKafedraByNameEn(String name);
    boolean existsKafedraByNameEn(String name);


//    @Query(value = "select f1.count as comeCount,f2.count as allCount from (\n" +
//            "        select t.kafedra_id,count(card.cardNo) as count from\n" +
//            "            (select  al.card_no as cardNo\n" +
//            "             from acc_monitor_log al\n" +
//            "                      join users u\n" +
//            "                           on cast(u.RFID as varchar) =\n" +
//            "                              cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "                      join users_Role ur\n" +
//            "                           on u.id = ur.users_id\n" +
//            "                      join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
//            "                           on ur.roles_id = role.id\n" +
//            "             where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
//            "             group by al.card_no\n" +
//            "            ) as card\n" +
//            "                join users u on cast(card.cardNo as varchar) =\n" +
//            "                                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "                join Teacher t on u.id = t.user_id\n" +
//            "        where t.kafedra_id=:kafedraId\n" +
//            "        group by t.kafedra_id\n" +
//            "    ) as f1\n" +
//            "        right join (\n" +
//            "    select t.kafedra_id,count(t.id) as count from Teacher t\n" +
//            "    where t.kafedra_id=:kafedraId\n" +
//            "    group by t.kafedra_id\n" +
//            ") as f2 on f2.kafedra_id = f1.kafedra_id",nativeQuery = true)
//    TeacherComeStatistics getComeCountForRektor(@Param("kafedraId") String kafedraId);

    @Query(value = "select f1.count as comeCount,f2.count as allCount from (\n" +
            "   select card.kafedra_id,count(card.cardNo) as count from\n" +
            "       (select  u.RFID as cardNo,t.kafedra_id\n" +
            "        from acc_monitor_log al\n" +
            "             join users u\n" +
            "                  on cast(u.RFID as varchar) =\n" +
            "                     cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "             join users_Role ur\n" +
            "                  on u.id = ur.users_id\n" +
            "             join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                  on ur.roles_id = role.id\n" +
            "             join Teacher t on u.id = t.user_id\n" +
            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and t.kafedra_id=:kafedraId\n" +
            "        group by u.RFID, t.kafedra_id\n" +
            "       ) as card\n" +
            "   group by card.kafedra_id\n" +
            ") as f1\n" +
            "   right join (\n" +
            "    select t.kafedra_id,count(t.id) as count from Teacher t where t.kafedra_id=:kafedraId group by t.kafedra_id\n" +
            ") as f2 on f2.kafedra_id = f1.kafedra_id",nativeQuery = true)
    TeacherComeStatistics getComeCountForRektor(@Param("kafedraId") String kafedraId);


//    @Query(value = "select f1.count as comeCount,f2.count as allCount from (\n" +
//            "   select S.section_id,count(card.cardNo) as count from\n" +
//            "       (select  al.card_no as cardNo\n" +
//            "        from acc_monitor_log al\n" +
//            "                 join users u\n" +
//            "                      on cast(u.RFID as varchar) =\n" +
//            "                         cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
//            "        group by al.card_no\n" +
//            "       ) as card\n" +
//            "           join users u on cast(card.cardNo as varchar) =\n" +
//            "                           cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "           join Staff S on u.id = S.user_id\n" +
//            "   where S.section_id=:kafedraId \n" +
//            "   group by S.section_id\n" +
//            ") as f1\n" +
//            "   right join (\n" +
//            "    select s.section_id,count(s.id) as count from Staff s\n" +
//            "    where s.section_id=:kafedraId \n" +
//            "    group by s.section_id\n" +
//            ") as f2 on f2.section_id = f1.section_id",nativeQuery = true)
//    TeacherComeStatistics getStaffComeCountForRektor(@Param("kafedraId") String kafedraId);

    @Query(value = "select f1.count as comeCount,f2.count as allCount from (\n" +
            "       select card.section_id,count(card.cardNo) as count from\n" +
            "           (select  u.RFID as cardNo, S.section_id from acc_monitor_log al\n" +
            "                     join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                     join Staff S on u.id = S.user_id\n" +
            "            where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and S.section_id=:kafedraId\n" +
            "            group by u.RFID, S.section_id\n" +
            "           ) as card\n" +
            "            group by card.section_id\n" +
            "   ) as f1\n" +
            "    right join (\n" +
            "    select s.section_id,count(s.id) as count from Staff s where s.section_id=:kafedraId\n" +
            "    group by s.section_id\n" +
            ") as f2 on f2.section_id = f1.section_id",nativeQuery = true)
    TeacherComeStatistics getStaffComeCountForRektor(@Param("kafedraId") String kafedraId);

//    @Query(value = "select f1.count as comeCount,f2.count as allCount from (\n" +
//            "   select S.dekanat_id,count(u.id) as count from\n" +
//            "       (select  al.card_no as cardNo\n" +
//            "        from acc_monitor_log al\n" +
//            "                 join users u\n" +
//            "                      on cast(u.RFID as varchar) =\n" +
//            "                         cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
//            "        group by al.card_no\n" +
//            "       ) as card\n" +
//            "           join users u on cast(card.cardNo as varchar) =\n" +
//            "                           cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "           join Staff S on u.id = S.user_id\n" +
//            "   where S.dekanat_id=:kafedraId\n" +
//            "   group by S.dekanat_id\n" +
//            ") as f1\n" +
//            "   right join (\n" +
//            "    select s.dekanat_id,count(s.id) as count from Staff s\n" +
//            "    where s.dekanat_id=:kafedraId\n" +
//            "    group by S.dekanat_id\n" +
//            ") as f2 on f2.dekanat_id = f1.dekanat_id\n",nativeQuery = true)
//    TeacherComeStatistics getStaffComeCountForRektor2(@Param("kafedraId") String kafedraId);

    @Query(value = "select f1.count as comeCount,f2.count as allCount from (\n" +
            "       select card.dekanat_id,count(card.cardNo) as count from\n" +
            "           (select  u.RFID as cardNo,S.dekanat_id\n" +
            "            from acc_monitor_log al\n" +
            "                     join users u  on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                     join Staff S on u.id = S.user_id\n" +
            "            where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and S.dekanat_id=:kafedraId\n" +
            "            group by u.RFID,S.dekanat_id\n" +
            "           ) as card\n" +
            "       group by card.dekanat_id\n" +
            "   ) as f1\n" +
            "       right join (\n" +
            "    select s.dekanat_id,count(s.id) as count from Staff s\n" +
            "    where s.dekanat_id=:kafedraId\n" +
            "    group by S.dekanat_id\n" +
            ") as f2 on f2.dekanat_id = f1.dekanat_id",nativeQuery = true)
    TeacherComeStatistics getStaffComeCountForRektor2(@Param("kafedraId") String kafedraId);


    @Query(value = "select k.nameEn as name from Kafedra k where k.id=:id",nativeQuery = true)
    String getNameOfKafedraById(@Param("id") String id);

    @Query(value = "select id as value, nameEn as label from Kafedra order by nameEn",nativeQuery = true)
    List<UserForTeacherSaveItem> getKafedraForTeacherSaving();

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Student s\n" +
            "   join users u on s.user_id = u.id where s.group_id=:id and s.teachStatus='TEACHING'",nativeQuery = true)
    List<GetTeachersForDekan31> getMonthlyStudentsOfGroupForDean31(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Student s\n" +
            "   join users u on s.user_id = u.id where s.group_id=:id  and s.teachStatus='TEACHING'",nativeQuery = true)
    List<GetTeachersForDekan30> getMonthlyStudentsOfGroupForDean30(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Student s\n" +
            "   join users u on s.user_id = u.id where s.group_id=:id and s.teachStatus='TEACHING'",nativeQuery = true)
    List<GetTeachersForDekan29> getMonthlyStudentsOfGroupForDean29(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Student s\n" +
            "   join users u on s.user_id = u.id where s.group_id=:id and s.teachStatus='TEACHING'",nativeQuery = true)
    List<GetTeachersForDekan28> getMonthlyStudentsOfGroupForDean28(@Param("id") String id, @Param("date") Date date);

    //**

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersForDekan31> getMonthlyTeachersOfKafedraForRektor31(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersForDekan30> getMonthlyTeachersOfKafedraForRektor30(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersForDekan29> getMonthlyTeachersOfKafedraForRektor29(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersForDekan28> getMonthlyTeachersOfKafedraForRektor28(@Param("id") String id, @Param("date") Date date);
    //**


    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra31> getTeachersOfKafedraForRektor31(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra30> getTeachersOfKafedraForRektor30(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra29> getTeachersOfKafedraForRektor29(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra28> getTeachersOfKafedraForRektor28(@Param("id") String id);

    @Query(value = "select T.rate,u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra31> getTeachersOfKafedra31(@Param("id") String id);

    @Query(value = "select T.rate,u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra30> getTeachersOfKafedra30(@Param("id") String id);

    @Query(value = "select T.rate,u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra29> getTeachersOfKafedra29(@Param("id") String id);

    @Query(value = "select T.rate,u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id",nativeQuery = true)
    List<GetTeachersOfKafedra28> getTeachersOfKafedra28(@Param("id") String id);

    @Query(value = "select id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date from users where id=:id",nativeQuery = true)
    GetDate31 getDate31(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date from users where id=:id",nativeQuery = true)
    GetDate30 getDate30(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date from users where id=:id",nativeQuery = true)
    GetDate29 getDate29(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date from users where id=:id",nativeQuery = true)
    GetDate28 getDate28(@Param("date") Date date, @Param("id") String id);


    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyTeachersForRektor31 getDateForRektor31(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyTeachersForRektor30 getDateForRektor30(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyTeachersForRektor29 getDateForRektor29(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyTeachersForRektor28 getDateForRektor28(@Param("date") Date date, @Param("id") String id);


    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    T.rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "        as date\n" +
            "from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id and u.id in :teachersIds",nativeQuery = true)
    List<GetTeachersForDekan31> getDateForTable31(@Param("id") String id, @Param("date") Date date,@Param("teachersIds") Set<String> teachersIds);



    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    T.rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "        as date\n" +
            "from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id and u.id in :teachersIds",nativeQuery = true)
    List<GetTeachersForDekan30> getDateForTable30(@Param("id") String id, @Param("date") Date date,@Param("teachersIds") Set<String> teachersIds);


    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    T.rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "        as date\n" +
            "from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id and u.id in :teachersIds",nativeQuery = true)
    List<GetTeachersForDekan29> getDateForTable29(@Param("id") String id, @Param("date") Date date,@Param("teachersIds") Set<String> teachersIds);

    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    T.rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "        as date\n" +
            "from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =:id and u.id in :teachersIds",nativeQuery = true)
    List<GetTeachersForDekan28> getDateForTable28(@Param("id") String id, @Param("date") Date date,@Param("teachersIds") Set<String> teachersIds);





    @Query(value = "select k.id, k.nameEn as name from Kafedra k join KafedraMudiri km on km.kafedra_id=k.id where km.user_id=:userId",nativeQuery = true)
    KafedraResDto getKafedraNameByUserId(@Param("userId") String userId);


/*    @Query(value = "select  f2.kafedra_id as kafedraId,f1.count as comeCount,f2.count as allCount from (\n" +
            "    select t.kafedra_id,count(card.cardNo) as count from\n" +
            "        (select  al.card_no as cardNo\n" +
            "         from acc_monitor_log al\n" +
            "                  join users u\n" +
            "                       on cast(u.RFID as varchar) =\n" +
            "                          cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                  join users_Role ur\n" +
            "                       on u.id = ur.users_id\n" +
            "                  join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                       on ur.roles_id = role.id\n" +
            "         where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "         group by al.card_no\n" +
            "        ) as card\n" +
            "            join users u on cast(card.cardNo as varchar) =\n" +
            "                            cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "            join Teacher t on u.id = t.user_id\n" +
            "            join Kafedra K on t.kafedra_id = K.id\n" +
            "            join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraId\n" +
            "    group by t.kafedra_id\n" +
            ") as f1\n" +
            "    right join (\n" +
            "    select t.kafedra_id,count(t.id) as count from Teacher t\n" +
            "  join Kafedra K on t.kafedra_id = K.id\n" +
            "  join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraId\n" +
            "    group by t.kafedra_id\n" +
            ") as f2 on f2.kafedra_id = f1.kafedra_id",nativeQuery = true)
    ComeCountTodayStatistics getComeCountTodayStatistics(@Param("kafedraId") String id);*/

    @Query(value = "select  f2.kafedra_id as kafedraId,f1.count as comeCount,f2.count as allCount from (\n" +
            "   select t.kafedra_id,count(card.cardNo) as count from\n" +
            "       (select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "                 join users u\n" +
            "                      on cast(u.RFID as varchar) =\n" +
            "                         cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "        group by al.card_no\n" +
            "       ) as card\n" +
            "           join users u on cast(card.cardNo as varchar) =\n" +
            "                           cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "           join Teacher t on u.id = t.user_id\n" +
            "   where t.kafedra_id=:kafedraId\n" +
            "   group by t.kafedra_id\n" +
            ") as f1\n" +
            "   right join (\n" +
            "    select t.kafedra_id,count(t.id) as count from Teacher t\n" +
            "    where t.kafedra_id=:kafedraId\n" +
            "    group by t.kafedra_id\n" +
            ") as f2 on f2.kafedra_id = f1.kafedra_id",nativeQuery = true)
    ComeCountTodayStatistics getComeCountTodayStatistics(@Param("kafedraId") String id);


    @Query(value = "select u.id,u.fullName,u.email,u.RFID,u.login,u.passportNum as passport\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) =\n" +
            "                 cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "              on ur.roles_id = role.id\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            "where t.kafedra_id=:kafedraId and al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "group by u.id, u.fullName, u.email, u.RFID, u.login, u.passportNum",nativeQuery = true)
    List<ComeStatistics> getStatisticsForKafedraDashboardCome(@Param("kafedraId") String kafedraId);

    @Query(value = "select f1.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "       select t.kafedra_id,t.user_id,card.time from\n" +
            "           (select  al.card_no as cardNo,al.time\n" +
            "            from acc_monitor_log al\n" +
            "                     join users u\n" +
            "                          on cast(u.RFID as varchar) =\n" +
            "                             cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                     join users_Role ur\n" +
            "                          on u.id = ur.users_id\n" +
            "                     join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                          on ur.roles_id = role.id\n" +
            "            where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "           ) as card\n" +
            "               join users u on cast(card.cardNo as varchar) =\n" +
            "                               cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "               join Teacher t on u.id = t.user_id\n" +
            "       where t.kafedra_id=:kafedraId\n" +
            "       group by t.kafedra_id,t.user_id,card.time\n" +
            "   ) as f1  join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "                                                                                                   join users u2 on t.user_id = u2.id\n" +
            "    where t.kafedra_id=:kafedraId\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id group by f1.user_id, f2.fullName, f2.email, f2.RFID, f2.login, f2.passportNum",nativeQuery = true)
    List<ComeStatistics> getStatisticsForKafedraDashboardComeForRektor(@Param("kafedraId") String kafedraId);


    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport  from (\n" +
            "               select t.kafedra_id,t.user_id from\n" +
            "                   (select  al.card_no as cardNo\n" +
            "                    from acc_monitor_log al\n" +
            "                             join users u\n" +
            "                                  on cast(u.RFID as varchar) =\n" +
            "                                     cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                             join users_Role ur\n" +
            "                                  on u.id = ur.users_id\n" +
            "                             join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                                  on ur.roles_id = role.id\n" +
            "                    where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "                   ) as card\n" +
            "                       join users u on cast(card.cardNo as varchar) =\n" +
            "                                       cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                       join Teacher t on u.id = t.user_id\n" +
            "               where t.kafedra_id=:kafedraId\n" +
            "               group by t.kafedra_id,t.user_id\n" +
            "           ) as f1 right join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "     join users u2 on t.user_id = u2.id where t.kafedra_id=:kafedraId\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login,u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.kafedra_id is null",nativeQuery = true)
    List<NoComeStatistics> getStatisticsForKafedraDashboardNoCome(@Param("kafedraId") String kafedraId);

    @Query(value = "select Top 1 al.time from acc_monitor_log al join users u on cast(u.RFID as varchar) =cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between getdate() and DATEADD(d, 1, getdate()) and u.id=:id order by al.time asc\n",nativeQuery = true)
    Date getEnterTime(@Param("id") String id);

    @Query(value = "select u.id,u.fullName from users u join Teacher t on u.id = t.user_id where t.kafedra_id=:kafedraId and (u.fullName like :searchParam or u.passportNum like :searchParam or u.login like :searchParam or u.RFID like :searchParam)",nativeQuery = true)
    List<SearchUserForDekanUseSendMessage> getTeachersForSendSms(@Param("kafedraId") String kafedraId, @Param("searchParam") String searchParam);

    @Query(value = "select u.id as value,u.fullName as label from users u\n" +
            "join users_Role uR on u.id = uR.users_id\n" +
            "join Role R2 on R2.id = uR.roles_id\n" +
            "left join KafedraMudiri K on u.id = K.user_id\n" +
            "where R2.roleName='ROLE_KAFEDRA' and K.user_id is null",nativeQuery = true)
    List<UserForTeacherSaveItem> getKafedraForSaved();

    @Query(value = "select K.id as value, K.nameEn as label from KafedraMudiri  KM\n" +
            "right join Kafedra K on KM.kafedra_id = K.id\n" +
            "where KM.kafedra_id is null",nativeQuery = true)
    List<UserForTeacherSaveItem> getKafedraForKafedraMudiriSaved();


    @Query(value = "select id as value, nameEn as label from  Kafedra order by nameEn",nativeQuery = true)
    List<FacultiesResDto> getKafedrasForSelect();

    @Query(value = "select u.id as value,u.fullName as label from Teacher t join users u on u.id=t.user_id where kafedra_id=:kafedraId order by u.fullName",nativeQuery = true)
    List<FacultiesResDto> getTeachersForSelectByKafedraId(@Param("kafedraId") String kafedraId);

    @Query(value = "select u.id as value,u.fullName as label from Teacher t\n" +
            "    join users u on u.id=t.user_id  join users_Position uP on u.id = uP.users_id join Position P on uP.positions_id = P.id where kafedra_id=:kafedraId and t.workerStatus='ASOSIY' order by  P.degree,u.fullName",nativeQuery = true)
    List<FacultiesResDto> getTeachersForTableByKafedraId(@Param("kafedraId") String kafedraId);
}
