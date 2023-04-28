package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.payload.resDto.TeacherCountComeAndAll;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeCountTodayStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.monthly.GetTeachersForDekan31;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly.*;
import uz.yeoju.yeoju_app.payload.resDto.section.SectionData;

import java.util.Date;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section, String> {
    Section getSectionByName(String name);
    boolean existsSectionByName(String name);

    @Query(value = "select s.id,s.name from Section s join Staff s2 on s.id = s2.section_id join users u2 on s2.user_id = u2.id join users_Role uR on u2.id = uR.users_id join (select * from Role where roleName='ROLE_REKTOR') as role on role.id=uR.roles_id",nativeQuery = true)
    SectionData getSectionDatas(@Param("userId") String userId);


    @Query(value = "select s.id,s.name from Section s join Staff s2 on s.id = s2.section_id where s2.user_id=:userId",nativeQuery = true)
    SectionData getSectionDatasByUserId(@Param("userId") String userId);


    @Query(value = "select  f2.section_id as kafedraId,f1.count as comeCount,f2.count as allCount from (\n" +
            "   select s.section_id,count(card.cardNo) as count from\n" +
            "       (select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "             join users u\n" +
            "                  on cast(u.RFID as varchar) =\n" +
            "                     cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "        group by al.card_no\n" +
            "       ) as card\n" +
            "           join users u on cast(card.cardNo as varchar) =\n" +
            "                           cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "           join Staff s on u.id = s.user_id\n" +
            "           join Section s2 on s.section_id = s2.id\n" +
            "           join Staff s3 on s2.id = s3.section_id\n" +
            "   where s3.user_id=:kafedraId\n" +
            "   group by s.section_id\n" +
            ") as f1\n" +
            "   right join (\n" +
            "    select s.section_id,count(s.id) as count from Staff s\n" +
            "      join Section s2 on s.section_id = s2.id\n" +
            "      join Staff s3 on s2.id = s3.section_id\n" +
            "    where s3.user_id=:kafedraId\n" +
            "    group by s.section_id\n" +
            ") as f2 on f2.section_id = f1.section_id",nativeQuery = true)
    ComeCountTodayStatistics getStaffComeCountTodayStatistics(@Param("kafedraId") String id);


    @Query(value = "select  f2.section_id as kafedraId,f1.count as comeCount,f2.count as allCount from (\n" +
            "   select s.section_id,count(card.cardNo) as count from\n" +
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
            "           join Staff s on u.id = s.user_id\n" +
            "           join Section s2 on s.section_id = s2.id\n" +
            "           join Staff s3 on s2.id = s3.section_id\n" +
            "           join users u2 on s3.user_id = u2.id\n" +
            "           join users_Role uR on u2.id = uR.users_id\n" +
            "           join (select * from Role where roleName='ROLE_REKTOR') as role on role.id=uR.roles_id\n" +
            "   group by s.section_id\n" +
            ") as f1\n" +
            "   right join (\n" +
            "    select s.section_id,count(s.id) as count from Staff s\n" +
            "      join Section s2 on s.section_id = s2.id\n" +
            "      join Staff s3 on s2.id = s3.section_id\n" +
            "      join users u2 on s3.user_id = u2.id\n" +
            "      join users_Role uR on u2.id = uR.users_id\n" +
            "      join (select * from Role where roleName='ROLE_REKTOR') as role on role.id=uR.roles_id\n" +
            "    group by s.section_id\n" +
            ") as f2 on f2.section_id = f1.section_id",nativeQuery = true)
    ComeCountTodayStatistics getStaffComeCountTodayStatistics();


    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport  from (\n" +
            "select s.section_id,s.user_id from\n" +
            "   (select  al.card_no as cardNo\n" +
            "    from acc_monitor_log al\n" +
            "             join users u\n" +
            "                  on cast(u.RFID as varchar) =\n" +
            "                     cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "    where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "   ) as card\n" +
            "       join users u on cast(card.cardNo as varchar) =\n" +
            "                       cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "       join Staff s on u.id = s.user_id\n" +
            "    where s.section_id=:kafedraId\n" +
            "group by s.section_id,s.user_id\n" +
            ") as f1 right join (\n" +
            "    select s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Staff s\n" +
            "   join users u2 on s.user_id = u2.id where s.section_id=:kafedraId\n" +
            "    group by u2.fullName, s.user_id, s.section_id, u2.email, u2.RFID, u2.login,u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.section_id is null",nativeQuery = true)
    List<NoComeStatistics> getStatisticsForSectionDashboardNoCome(@Param("kafedraId") String kafedraId);


    @Query(value = "select f1.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "  select s.section_id,s.user_id,card.time from\n" +
            "      (select  al.card_no as cardNo,al.time\n" +
            "       from acc_monitor_log al\n" +
            "                join users u\n" +
            "                     on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "       where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) =\n" +
            "                          cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Staff s on u.id = s.user_id\n" +
            "  where s.section_id=:kafedraId\n" +
            "  group by s.section_id,s.user_id,card.time\n" +
            ") as f1  join (\n" +
            "    select s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Staff s\n" +
            " join users u2 on s.user_id = u2.id\n" +
            "    where s.section_id=:kafedraId\n" +
            "    group by s.section_id, s.user_id, u2.fullName, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id group by f1.user_id, f2.fullName, f2.email, f2.RFID, f2.login, f2.passportNum",nativeQuery = true)
    List<ComeStatistics> getStatisticsForSectionDashboardCome(@Param("kafedraId") String kafedraId);


    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
            " join users u on s.user_id = u.id\n" +
            "where s.section_id=( select s.id from Section s join Staff S2 on s.id = S2.section_id where S2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra31> getStaffsOfKafedra31(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
            " join users u on s.user_id = u.id\n" +
            "where s.section_id=( select s.id from Section s join Staff S2 on s.id = S2.section_id where S2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra30> getStaffsOfKafedra30(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
            " join users u on s.user_id = u.id\n" +
            "where s.section_id=( select s.id from Section s join Staff S2 on s.id = S2.section_id where S2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra29> getStaffsOfKafedra29(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
            " join users u on s.user_id = u.id\n" +
            "where s.section_id=( select s.id from Section s join Staff S2 on s.id = S2.section_id where S2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra28> getStaffsOfKafedra28(@Param("id") String id);


    @Query(value = " select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s join users u on s.user_id = u.id\n" +
            "where s.dekanat_id=( select d.id from Dekanat d join Dekan d2 on d.id = d2.dekanat_id where d2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra31> getStaffsOfDekan31(@Param("id") String id);

    @Query(value = " select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s join users u on s.user_id = u.id\n" +
            "where s.dekanat_id=( select d.id from Dekanat d join Dekan d2 on d.id = d2.dekanat_id where d2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra30> getStaffsOfDekan30(@Param("id") String id);

    @Query(value = " select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s join users u on s.user_id = u.id\n" +
            "where s.dekanat_id=( select d.id from Dekanat d join Dekan d2 on d.id = d2.dekanat_id where d2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra29> getStaffsOfDekan29(@Param("id") String id);

    @Query(value = " select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s join users u on s.user_id = u.id\n" +
            "where s.dekanat_id=( select d.id from Dekanat d join Dekan d2 on d.id = d2.dekanat_id where d2.user_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra28> getStaffsOfDekan28(@Param("id") String id);


    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor31 getDateForRektor31(@Param("date") Date date,@Param("id") String sectionId);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor30 getDateForRektor30(@Param("date") Date date,@Param("id") String sectionId);
    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor29 getDateForRektor29(@Param("date") Date date,@Param("id") String sectionId);
    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor28 getDateForRektor28(@Param("date") Date date,@Param("id") String sectionId);


    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Staff s join users u on s.user_id = u.id where (s.section_id =:id or s.dekanat_id=:id)",nativeQuery = true)
    List<GetStaffsForDekan31> getMonthlyStaffsOfSectionForRektor31(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Staff s join users u on s.user_id = u.id where (s.section_id =:id or s.dekanat_id=:id)",nativeQuery = true)
    List<GetStaffsForDekan30> getMonthlyStaffsOfSectionForRektor30(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Staff s join users u on s.user_id = u.id where (s.section_id =:id or s.dekanat_id=:id)",nativeQuery = true)
    List<GetStaffsForDekan29> getMonthlyStaffsOfSectionForRektor29(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Staff s join users u on s.user_id = u.id where (s.section_id =:id or s.dekanat_id=:id)",nativeQuery = true)
    List<GetStaffsForDekan28> getMonthlyStaffsOfSectionForRektor28(@Param("id") String id, @Param("date") Date date);

    @Query(value = "select count(f.cardNo) as count\n" +
            "from (select al.card_no as cardNo\n" +
            "      from acc_monitor_log al\n" +
            "               join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "               join Staff s on u.id = s.user_id\n" +
            "               join (\n" +
            "          select TOP 1 s2.id as id from Section s2 join Staff s3 on s2.id = s3.section_id\n" +
            "              join users u2 on s3.user_id = u2.id\n" +
            "              join users_Role uR on u2.id = uR.users_id\n" +
            "              join (select * from Role where roleName='ROLE_REKTOR') as role on role.id=uR.roles_id\n" +
            "      ) as section on section.id = s.section_id\n" +
            "      where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "      group by al.card_no) as f\n" +
            "union\n" +
            "select count(s.id) as count from Staff s\n" +
            "    join ( select TOP 1 s2.id as id from Section s2 join Staff s3 on s2.id = s3.section_id join users u2 on s3.user_id = u2.id\n" +
            "     join users_Role uR on u2.id = uR.users_id\n" +
            "     join (select * from Role where roleName='ROLE_REKTOR') as role on role.id=uR.roles_id) as section on section.id=s.section_id\n" +
            "group by s.section_id",nativeQuery = true)
    List<Integer> getCountComeAndAll(@Param("userId") String userId);


//    @Query(value = "select f2.section_id as id, f1.count as comeCount,f2.count as allCount from (\n" +
//            "    select s.section_id,count(card.cardNo) as count from\n" +
//            "        (select  al.card_no as cardNo\n" +
//            "         from acc_monitor_log al\n" +
//            "              join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "         where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
//            "         group by al.card_no\n" +
//            "        ) as card\n" +
//            "            join users u on cast(card.cardNo as varchar) =  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS join Staff s on u.id = s.user_id\n" +
//            "            join ( select TOP 1 s2.id as id from Section s2 join Staff s3 on s2.id = s3.section_id where s3.user_id=:userId) as section on section.id=s.section_id\n" +
//            "    group by s.section_id\n" +
//            ") as f1\n" +
//            "    right join (\n" +
//            "    select s.section_id,count(s.id) as count from Staff s\n" +
//            "    join ( select TOP 1 s2.id as id from Section s2 join Staff s3 on s2.id = s3.section_id where s3.user_id=:userId) as section on section.id=s.section_id\n" +
//            "    group by s.section_id\n" +
//            ") as f2 on f2.section_id = f1.section_id",nativeQuery = true)
//    List<TeacherCountComeAndAll> getCountComeAndAll(@Param("userId") String userId);
}
