package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.staff.Staff;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StaffCountComeAndAll;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraMudiriForRektorTeacherPage;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly.MonthlyStaffsForRektor28;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly.MonthlyStaffsForRektor29;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly.MonthlyStaffsForRektor30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly.MonthlyStaffsForRektor31;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, String> {

    Boolean existsStaffByUserIdAndSectionId(String user_id, String section_id);
    Boolean existsStaffByUserId(String user_id);

    Optional<Staff> findStaffByUserId(String user_id);

     @Query(value = "select TOP 1 u.id,u.fullName,u.email from Staff s\n" +
             "                                              join users u on s.user_id = u.id\n" +
             "                                              join users_Role uR on u.id = uR.users_id\n" +
             "                                              join Role R2 on uR.roles_id = R2.id\n" +
             "where (s.section_id=:id or s.dekanat_id=:id) and R2.roleName = 'ROLE_DEKAN' order by R2.createdAt asc",nativeQuery = true)
    KafedraMudiriForRektorTeacherPage getBossOfSectionForRektorStaffPageDekan(@Param("id") String id);

    @Query(value = "select TOP 1 u.id,u.fullName,u.email from Staff s\n" +
            "                                              join users u on s.user_id = u.id\n" +
            "                                              join users_Role uR on u.id = uR.users_id\n" +
            "                                              join Role R2 on uR.roles_id = R2.id\n" +
            "where s.section_id=:id order by R2.createdAt asc",nativeQuery = true)
    KafedraMudiriForRektorTeacherPage getBossOfSectionForRektorStaffPage(@Param("id") String id);


    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport  from (\n" +
            "   select case when s.section_id IS NOT NULL\n" +
            "                   then s.section_id\n" +
            "               else s.dekanat_id\n" +
            "              end as section_id,s.user_id from\n" +
            "       (select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "             join users u  on cast(u.RFID as varchar) =\n" +
            "                     cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "             join Staff s on u.id = s.user_id\n" +
            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "       ) as card\n" +
            "           join users u on cast(card.cardNo as varchar) =\n" +
            "                           cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "           join Staff s on u.id = s.user_id\n" +
            "   where (s.section_id=:kafedraId or s.dekanat_id=:kafedraId)\n" +
            "   group by  case when s.section_id IS NOT NULL\n" +
            "                      then s.section_id\n" +
            "                  else s.dekanat_id\n" +
            "                 end,s.user_id\n" +
            ") as f1 right join (\n" +
            "    select s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Staff s\n" +
            "    join users u2 on s.user_id = u2.id where (s.section_id=:kafedraId or s.dekanat_id=:kafedraId)\n" +
            "    group by s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.section_id is null",nativeQuery = true)
    List<NoComeStatistics> getStatisticsForRektorStaffNoCome(@Param("kafedraId") String kafedraId);

    @Query(value = "select f1.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "          select case when s.section_id <> null\n" +
            "                          then s.section_id\n" +
            "                      else s.dekanat_id\n" +
            "                     end as section_id,s.user_id,card.time from\n" +
            "              (select  al.card_no as cardNo,al.time\n" +
            "               from acc_monitor_log al\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                        join Staff S on u.id = S.user_id\n" +
            "               where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "              ) as card\n" +
            "                  join users u on cast(card.cardNo as varchar) = cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                  join Staff s on u.id = s.user_id\n" +
            "          where (s.section_id=:kafedraId or s.dekanat_id=:kafedraId)\n" +
            "          group by case when s.section_id <> null\n" +
            "                            then s.section_id\n" +
            "                        else s.dekanat_id\n" +
            "                       end,s.user_id,card.time\n" +
            "      ) as f1  join (\n" +
            "    select s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Staff s\n" +
            "   join users u2 on s.user_id = u2.id where (s.section_id=:kafedraId or s.dekanat_id=:kafedraId) group by s.section_id, s.user_id, u2.fullName, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id group by f1.user_id, f2.fullName, f2.email, f2.RFID, f2.login, f2.passportNum",nativeQuery = true)
    List<ComeStatistics> getStatisticsForRektorStaffComeForRektor(@Param("kafedraId") String kafedraId);

    /*    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport  from (\n" +
            "   select s.section_id,s.user_id from\n" +
            "       (select  al.card_no as cardNo\n" +
            "        from acc_monitor_log al\n" +
            "             join users u\n" +
            "                  on cast(u.RFID as varchar) =\n" +
            "                     cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "            join Staff s on u.id = s.user_id\n" +
            "        where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "       ) as card\n" +
            "           join users u on cast(card.cardNo as varchar) =\n" +
            "                           cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "           join Staff s on u.id = s.user_id\n" +
            "   where s.section_id=:kafedraId\n" +
            "   group by s.section_id,s.user_id\n" +
            ") as f1 right join (\n" +
            "    select s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Staff s\n" +
            "   join users u2 on s.user_id = u2.id where s.section_id=:kafedraId\n" +
            "    group by s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.section_id is null",nativeQuery = true)
    List<NoComeStatistics> getStatisticsForRektorStaffNoCome(@Param("kafedraId") String kafedraId);*/
   /* @Query(value = "select f1.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "  select s.section_id,s.user_id,card.time from\n" +
            "      (select  al.card_no as cardNo,al.time\n" +
            "       from acc_monitor_log al\n" +
            "        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "        join Staff S on u.id = S.user_id\n" +
            "       where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) = cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Staff s on u.id = s.user_id\n" +
            "  where s.section_id=:kafedraId\n" +
            "  group by s.section_id,s.user_id,card.time\n" +
            "  ) as f1  join (\n" +
            "    select s.section_id,s.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Staff s\n" +
            "    join users u2 on s.user_id = u2.id where s.section_id=:kafedraId group by s.section_id, s.user_id, u2.fullName, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id group by f1.user_id, f2.fullName, f2.email, f2.RFID, f2.login, f2.passportNum",nativeQuery = true)
    List<ComeStatistics> getStatisticsForRektorStaffComeForRektor(@Param("kafedraId") String kafedraId);*/


    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
            " join users u on s.user_id = u.id\n" +
            " join users_Role uR on u.id = uR.users_id\n" +
            " join Role R2 on uR.roles_id = R2.id\n" +
            "where (s.section_id=:id or s.dekanat_id=:id)\n" +
            "group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
    List<GetTeachersOfKafedra31> getStaffsOfSectionForRektor31(@Param("id") String id);


     @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
             " join users u on s.user_id = u.id\n" +
             " join users_Role uR on u.id = uR.users_id\n" +
             " join Role R2 on uR.roles_id = R2.id\n" +
             "    where (s.section_id=:id or s.dekanat_id=:id)\n" +
             " group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
     List<GetTeachersOfKafedra30> getStaffsOfSectionForRektor30(@Param("id") String id);

     @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
             " join users u on s.user_id = u.id\n" +
             " join users_Role uR on u.id = uR.users_id\n" +
             " join Role R2 on uR.roles_id = R2.id\n" +
             "    where (s.section_id=:id or s.dekanat_id=:id)\n" +
             " group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
     List<GetTeachersOfKafedra29> getStaffsOfSectionForRektor29(@Param("id") String id);

     @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,u.nationality,u.citizenship from Staff s\n" +
             " join users u on s.user_id = u.id\n" +
             " join users_Role uR on u.id = uR.users_id\n" +
             " join Role R2 on uR.roles_id = R2.id\n" +
             "    where (s.section_id=:id or s.dekanat_id=:id)\n" +
             " group by u.id,u.fullName, u.id, u.passportNum, u.login, u.RFID, u.email, u.nationality, u.citizenship",nativeQuery = true)
     List<GetTeachersOfKafedra28> getStaffsOfSectionForRektor28(@Param("id") String id);

     @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
             "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
     MonthlyStaffsForRektor31 getDateForRektor31(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor30 getDateForRektor30(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor29 getDateForRektor29(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyStaffsForRektor28 getDateForRektor28(@Param("date") Date date, @Param("id") String id);

//     @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email,:date as date from Staff s\n" +
//             "    join users u on s.user_id = u.id\n" +
//             "where s.section_id =:id",nativeQuery = true)
//     List<GetTeachersForDekan31> getMonthlyStaffsOfSectionForRektor31(@Param("id") String id, @Param("date") Date date);

   @Query(value = "select f2.section_id as id, f1.count as comeCount,f2.count as allCount from (\n" +
           "        select s.section_id,count(card.cardNo) as count from\n" +
           "            (select  al.card_no as cardNo\n" +
           "             from acc_monitor_log al\n" +
           "                      join users u\n" +
           "                           on cast(u.RFID as varchar) =\n" +
           "                              cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
           "                      join users_Role ur\n" +
           "                           on u.id = ur.users_id\n" +
           "                      join (select id from Role where (roleName <> 'ROLE_TEACHER' and roleName <>'ROLE_STUDENT')) as role\n" +
           "                           on ur.roles_id = role.id\n" +
           "             where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0)\n" +
           "             group by al.card_no\n" +
           "            ) as card\n" +
           "                join users u on cast(card.cardNo as varchar) =\n" +
           "                                cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
           "                join Staff s on u.id = s.user_id\n" +
           "        group by s.section_id\n" +
           "    ) as f1\n" +
           "        right join (\n" +
           "    select s.section_id,count(s.id) as count from Staff s\n" +
           "    group by s.section_id\n" +
           ") as f2 on f2.section_id = f1.section_id",nativeQuery = true)
    List<StaffCountComeAndAll> getStaffComeCountStatistics();
}
