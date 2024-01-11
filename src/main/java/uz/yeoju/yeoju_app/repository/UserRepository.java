package uz.yeoju.yeoju_app.repository;

import org.apache.tomcat.jni.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.resDto.admin.GetUserForUpdate;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentData;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday2;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.KafedraStatistics;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.RektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StaffDatasForRektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StudentDatasForRektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers28;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers29;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers31;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.*;
import uz.yeoju.yeoju_app.payload.resDto.search.SearchDto;
import uz.yeoju.yeoju_app.payload.resDto.staff.DataForStaffSaving;
import uz.yeoju.yeoju_app.payload.resDto.user.*;
import uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLoginOrEmailOrRFID(String login, String email, String RFID);
    User getUserByLogin(String login);
    User findUserByEmail(String email);

    Optional<User> findUserByPassportNum(String passportNum);
    Optional<User> findUserByLogin(String login);
    User findUserByRFID(String RFID);

    @Query(value = "select count(login) from users where login=?1 group by login",nativeQuery = true)
    Integer getCountByLogin(String login);

    boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID);

    @Query(value = "select TOP 1 login from users where passportNum=?1",nativeQuery = true)
    String getLoginByPassport(String passport);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff31 getSectionStaffsDataForRektorBySectionId31(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff30 getSectionStaffsDataForRektorBySectionId30(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff29 getSectionStaffsDataForRektorBySectionId29(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff28 getSectionStaffsDataForRektorBySectionId28(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff31No getSectionStaffsDataForRektorBySectionId31No(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff30No getSectionStaffsDataForRektorBySectionId30No(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff29No getSectionStaffsDataForRektorBySectionId29No(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    SectionStaff28No getSectionStaffsDataForRektorBySectionId28No(@Param("id") String id);


    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers31 getKafedraTeachersDataForRektorByKafedraId31(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers30 getKafedraTeachersDataForRektorByKafedraId30(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers29 getKafedraTeachersDataForRektorByKafedraId29(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers28 getKafedraTeachersDataForRektorByKafedraId28(@Param("id") String id);

    @Query(value = "select id,nameEn as name from Kafedra order by name asc ",nativeQuery = true)
    List<KafedraStatistics> getKafedraStatisticsForRektor();

    @Query(value = "select id,name from Section\n" +
            "where name <> 'Rahbariyat' order by name ",nativeQuery = true)
    List<StaffStatistics> getStaffStatisticsForRektor();

    @Query(value = "select id,name from Dekanat\n" +
            "order by name ",nativeQuery = true)
    List<DekanStaffStatistics> getStaffStatisticsForRektor2();

    @Query(value = "select u.id,u.email, u.login,u.fullName,u.bornYear,u.citizenship,u.nationality,u.passportNum,R2.roleName from users u join users_Role uR on u.id = uR.users_id\n" +
            "join Role R2 on uR.roles_id = R2.id\n" +
            "where (R2.roleName <> 'ROLE_STUDENT' AND R2.roleName<>'ROLE_TEACHER')",nativeQuery = true)
    List<StaffDatasForRektorDashboard> getStaffDataForRektorDashboard();

    @Query(value = "select id,login,fullName,bornYear,citizenship,nationality,passportNum from users",nativeQuery = true)
    List<StudentDatasForRektorDashboard> getStudentDataForRektorDashboard();

    @Query(value = "select t.user_id as id,t.workerStatus,u.fullName,u.email,u.RFID,u.login,u.passportNum as passport from Teacher t\n" +
            "join users u on t.user_id = u.id",nativeQuery = true)
    List<TeacherData> getTeacherDataForRektorDashboard();

    @Query(value = "select :id as id",nativeQuery = true)
    RektorDashboard getRektorDashboard(@Param("id") String id);

    @Query(value = "select u.id,u.fullName,u.rfid,u.passportNum as passport from users u join SmsLog sl on sl.userId=u.id where sl.id=:userId",nativeQuery = true)
    List<UserResDto> getUserFieldsForSMS(@Param("userId") String userId);

    @Query(value = "select count(*) from users\n" +
            "join users_Role uR on users.id = uR.users_id\n" +
            "join Role R2 on uR.roles_id = R2.id\n" +
            "where R2.id=:roleId",nativeQuery = true)
    Long countUsersByRoleId(@Param("roleId") String roleId);
    List<User> findUsersByRFID(String RFID);

    @Query(value = "select u.id as userId, u.fullName,u.login,u.passportNum as passport,r.roleName,u.accountNonLocked from users u\n" +
            "left join Student S on u.id = S.user_id\n" +
            "         join users_Role uR on u.id = uR.users_id\n" +
            "         join Role r on uR.roles_id = r.id\n" +
            "where login like :keyword or fullName like :keyword \n" +
            "or u.passportNum like :keyword",nativeQuery = true)
    List<SearchDto> getUserForSearch(@Param("keyword") String keyword);

    User getUserByPassportNum(String passportNum);

    @Query(value = "select u.id,u.login,u.fullName,u.bornYear,u.citizenship,u.nationality,u.passportNum,s.rektororder,s.lengthOfStudying,s.teachStatus from users u\n" +
            "join Student S on u.id = S.user_id\n" +
            "where u.id=:userId",nativeQuery = true)
    StudentData getStudentDataByUserId(@Param("userId") String userId);

    @Query(value = "select * from dbo.getStudentsData(?1)",nativeQuery = true)
    StudentData getStudentDataByUserIdNEW(String userId);


    @Query(value = "select u.id,u.login,u.fullName,u.bornYear,u.citizenship,u.nationality,u.passportNum,u.email, t.workerStatus  from users u join Teacher t on u.id = t.user_id where u.id=:userId",nativeQuery = true)
    TeacherData getTeacherDataByUserId(@Param("userId") String userId);


    @Query(value = "select id,fullName,email,bornYear,citizenship,nationality,passportNum from users where id=:userId",nativeQuery = true)
    UserField getUserFields(@Param("userId") String userId);

    @Query(value = "select TOP 50 id as value,fullName as label from users order by fullName asc",nativeQuery = true)
    List<UserForTeacherSaveItem> getUserForTeacherSaving();

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    UserForTeacherSave getItemsForTeacherSaving(@Param("id") String id);

//    @Query(value = "select id as value,fullName as label from users where fullName like :keyword order by fullName asc",nativeQuery = true)
//    List<UserForTeacherSaveItem> getUserForTeacherSavingSearch(@Param("keyword") String keyword);

    @Query(value = "select u.id as value,u.fullName as label,u.login as login,u.passportNum as passport from users u\n" +
            "left join users_Role uR on u.id = uR.users_id\n" +
            "join Role R2 on uR.roles_id = R2.id\n" +
            " where (u.fullName like :keyword or u.login like :keyword or u.passportNum like :keyword) order by fullName asc",nativeQuery = true)
    List<UserForTeacherSaveItem> getUserForTeacherSavingSearch(@Param("keyword") String keyword);

    @Query(value = "select u.id as value,u.fullName as label,u.firstname as firstName,u.lastName as lastName,u.middleName as middleName,u.login as login,u.passportNum as passport from users u\n" +
            "left join users_Role uR on u.id = uR.users_id\n" +
            "join Role R2 on uR.roles_id = R2.id\n" +
            " where (u.fullName like :keyword or u.login like :keyword or u.passportNum like :keyword) order by fullName asc",nativeQuery = true)
    List<UserForTeacherSaveItem2> getUserForTeacherSavingSearch2(@Param("keyword") String keyword);

    @Query(value = "select r.id as value,r.roleName as label from Role r order by r.roleName asc ",nativeQuery = true)
    List<UserForTeacherSaveItem> getRolesForStaffSaving();

    @Query(value = "select s.id as value,s.name as label from Section s order by s.name asc ",nativeQuery = true)
    List<UserForTeacherSaveItem> getSectionsForStaffSaving();

    @Query(value = "select :id as id",nativeQuery = true)
    DataForStaffSaving getDataForStaffSaving(@Param("id") String id);


    @Query(value = "select u.id,u.fullName,u.RFID,u.passportNum as passport,:year as year,:week as week,:weekday as weekday from users u join Teacher T on u.id = T.user_id where T.kafedra_id=:kafedraId",nativeQuery = true)
    List<UserForRoomStatistics2> getTeachersStatisticsForKafedraDashboardWithYearMonthDay(@Param("kafedraId") String kafedraId, @Param("year") Integer year, @Param("week") Integer week,@Param("weekday")Integer weekday);


    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "          select t.kafedra_id,t.user_id from\n" +
            "              (select  al.card_no as cardNo\n" +
            "               from acc_monitor_log al\n" +
            "                        join users u\n" +
            "                             on cast(u.RFID as varchar) =\n" +
            "                                cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                        join users_Role ur\n" +
            "                             on u.id = ur.users_id\n" +
            "                        join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                             on ur.roles_id = role.id\n" +
            "               where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.login=:pass\n" +
            "              ) as card\n" +
            "                  join users u on cast(card.cardNo as varchar) =\n" +
            "                                  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                  join Teacher t on u.id = t.user_id\n" +
            "                  join Kafedra K on t.kafedra_id = K.id\n" +
            "                  join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "          where KM.user_id=:kafedraId and u.login=:pass\n" +
            "          group by t.kafedra_id,t.user_id\n" +
            "      ) as f1\n" +
            "          right join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "   join users u2 on t.user_id = u2.id\n" +
            "   join Kafedra K on t.kafedra_id = K.id\n" +
            "   join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraId and u2.login=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.user_id is null",nativeQuery = true)
    TeacherData getTeachersForRemember(@Param("pass") String pass,@Param("kafedraId") String kafedraId);

    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "          select t.kafedra_id,t.user_id from\n" +
            "              (select  al.card_no as cardNo\n" +
            "               from acc_monitor_log al\n" +
            "                        join users u\n" +
            "                             on cast(u.RFID as varchar) =\n" +
            "                                cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                        join users_Role ur\n" +
            "                             on u.id = ur.users_id\n" +
            "                        join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                             on ur.roles_id = role.id\n" +
            "               where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.login=:pass\n" +
            "              ) as card\n" +
            "                  join users u on cast(card.cardNo as varchar) =\n" +
            "                                  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                  join Teacher t on u.id = t.user_id\n" +
            "                  join Kafedra K on t.kafedra_id = K.id\n" +
            "                  join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "          where KM.user_id=:kafedraId and u.login=:pass\n" +
            "          group by t.kafedra_id,t.user_id\n" +
            "      ) as f1\n" +
            "          right join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "   join users u2 on t.user_id = u2.id\n" +
            "   join Kafedra K on t.kafedra_id = K.id\n" +
            "   join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraId and u2.login=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.user_id is null",nativeQuery = true)
    TeacherData getTeachersForRememberLogin(@Param("pass") String pass,@Param("kafedraId") String kafedraId);


    //todo------------------ bugun uzgartirding 2023.01.26
    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "  select t.kafedra_id,t.user_id from\n" +
            "      (select  al.card_no as cardNo\n" +
            "       from acc_monitor_log al\n" +
            "                join users u\n" +
            "                     on cast(u.RFID as varchar) =\n" +
            "                        cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join users_Role ur\n" +
            "                     on u.id = ur.users_id\n" +
            "                join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                     on ur.roles_id = role.id\n" +
            "       where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.passportNum=:pass\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) =\n" +
            "                          cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Teacher t on u.id = t.user_id\n" +
            "          join Kafedra K on t.kafedra_id = K.id\n" +
            "          join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "  where KM.user_id=:kafedraMudiriId and u.passportNum=:pass\n" +
            "  group by t.kafedra_id,t.user_id\n" +
            ") as f1\n" +
            " right  join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "   join users u2 on t.user_id = u2.id\n" +
            "   join Kafedra K on t.kafedra_id = K.id\n" +
            "   join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraMudiriId and u2.passportNum=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id",nativeQuery = true)
    TeacherData getTeachersForRememberWithKafedraMudiriId(@Param("pass") String pass, @Param("kafedraMudiriId") String kafedraMudiriId);

    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "  select t.kafedra_id,t.user_id from\n" +
            "      (select  al.card_no as cardNo\n" +
            "       from acc_monitor_log al\n" +
            "                join users u\n" +
            "                     on cast(u.RFID as varchar) =\n" +
            "                        cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join users_Role ur\n" +
            "                     on u.id = ur.users_id\n" +
            "                join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                     on ur.roles_id = role.id\n" +
            "       where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.login=:pass\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) =\n" +
            "                          cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Teacher t on u.id = t.user_id\n" +
            "          join Kafedra K on t.kafedra_id = K.id\n" +
            "          join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "  where KM.user_id=:kafedraMudiriId and u.login=:pass\n" +
            "  group by t.kafedra_id,t.user_id\n" +
            ") as f1\n" +
            " right  join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "   join users u2 on t.user_id = u2.id\n" +
            "   join Kafedra K on t.kafedra_id = K.id\n" +
            "   join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraMudiriId and u2.login=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id",nativeQuery = true)
    TeacherData getTeachersForRememberWithKafedraMudiriIdLogin(@Param("pass") String pass, @Param("kafedraMudiriId") String kafedraMudiriId);


    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "  select t.kafedra_id,t.user_id from\n" +
            "      (select  al.card_no as cardNo\n" +
            "       from acc_monitor_log al\n" +
            "            join users u\n" +
            "                 on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "            join users_Role ur\n" +
            "                 on u.id = ur.users_id\n" +
            "            join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                 on ur.roles_id = role.id\n" +
            "       where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.passportNum=:pass\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) =  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Teacher t on u.id = t.user_id\n" +
            "  where t.kafedra_id=:kafedraId and u.passportNum=:pass\n" +
            "  group by t.kafedra_id,t.user_id\n" +
            ") as f1\n" +
            "  right  join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "               join users u2 on t.user_id = u2.id\n" +
            "    where t.kafedra_id=:kafedraId and u2.passportNum=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id",nativeQuery = true)
    TeacherData getTeachersForRememberWithKafedraId(@Param("pass") String pass, @Param("kafedraId") String kafedraId);

    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            "  select t.kafedra_id,t.user_id from\n" +
            "      (select  al.card_no as cardNo\n" +
            "       from acc_monitor_log al\n" +
            "            join users u\n" +
            "                 on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "            join users_Role ur\n" +
            "                 on u.id = ur.users_id\n" +
            "            join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
            "                 on ur.roles_id = role.id\n" +
            "       where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.login=:pass\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) =  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Teacher t on u.id = t.user_id\n" +
            "  where t.kafedra_id=:kafedraId and u.login=:pass\n" +
            "  group by t.kafedra_id,t.user_id\n" +
            ") as f1\n" +
            "  right  join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "               join users u2 on t.user_id = u2.id\n" +
            "    where t.kafedra_id=:kafedraId and u2.login=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id",nativeQuery = true)
    TeacherData getTeachersForRememberWithKafedraIdLogin(@Param("pass") String pass, @Param("kafedraId") String kafedraId);


    @Query(value = "select Top 1 id, fullName, email, RFID, login, passportNum as passport from users where passportNum=:pass ",nativeQuery = true)
    TeacherData getTeachersForRemember3(@Param("pass") String pass);

    @Query(value = "select Top 1 id, fullName, email, RFID, login, passportNum as passport from users where login=:pass ",nativeQuery = true)
    TeacherData getTeachersForRemember3Login(@Param("pass") String pass);

    @Query(value = "select u.id, u.fullName, ad.door_name as room from acc_monitor_log al\n" +
            "          join acc_door ad on ad.device_id=al.device_id\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.id=:userId\n" +
            "group by u.id, u.fullName, ad.door_name",nativeQuery = true)
    List<UserCheckRoomStatistics> getUserCheckRoomStatistics(@Param("userId") String userId);


    @Query(value = "select  u.id,u.fullName,ad.door_name as room,\n" +
            "          al.time,\n" +
            "        CASE\n" +
            "            WHEN\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    00,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 1\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    00,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 1\n" +
            "\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    10,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 2\n" +
            "\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    10,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    11,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 3\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    11,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    12,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 4\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    12,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    13,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 5\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    13,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    14,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 6\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    14,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    15,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 7\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    15,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    16,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 8\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    16,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    17,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 9\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    17,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    18,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 10\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    18,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    19,\n" +
            "                    52,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 11\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    19,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    21,\n" +
            "                    00,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 12\n" +
            "\n" +
            "            ELSE 0\n" +
            "            END as section\n" +
            "\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "      join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "     where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and ad.door_name=:room and u.id=:userId",nativeQuery = true)
    List<EachOneTimeOfRoomStatistics> getEachOneTimeOfRoomStatistics(@Param("userId") String userId,@Param("room") String room);


    @Query(value = "select id,fullName from users where id=:id",nativeQuery = true)
    UserForRoomStatistics getUserForRoomStatistics(@Param("id") String id);

    @Query(value = "select id,fullName,:week as week,:year as year from users where id=:id",nativeQuery = true)
    WeekStatisticsByWeek getUserForWeekStatisticsByWeek(@Param("id") String id,@Param("week") Integer week,@Param("year") Integer year);

    @Query(value = "select id,fullName from users where id=:id",nativeQuery = true)
    WeekStatistics getUserForWeekStatistics(@Param("id") String id);

    @Query(value = "select id,fullName from users where passportNum=:passport",nativeQuery = true)
    WeekStatistics getUserForWeekStatisticsByPassport(@Param("passport") String passport);

    @Query(value = "select  :userId as id, ad.door_name as room\n" +
            "from acc_monitor_log al\n" +
            "         join acc_door ad on ad.device_id=al.device_id and ad.door_no=al.event_point_id\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where ad.door_no=al.event_point_id and al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.id=:userId\n" +
            "group by u.id, ad.door_name",nativeQuery = true)
    List<RoomsForStatistics> getRoomsForStatistics(@Param("userId") String userId);



    @Query(value = "select  :userId as id, ad.door_name as room,:year as year ,:week as week,:weekday as weekday\n" +
            "from acc_monitor_log al\n" +
            "         join acc_door ad on ad.device_id=al.device_id and ad.door_no=al.event_point_id\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where ad.door_no=al.event_point_id and al.time between\n" +
            "    DATEADD(\n" +
            "            DAY,\n" +
            "            + (:weekday-1),\n" +
            "            DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week- 1,CAST('1/1/' + cast(:year as varchar) AS Date)))\n" +
            "        ) and\n" +
            "    DATEADD(\n" +
            "            DAY,\n" +
            "            + (:weekday),\n" +
            "            DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week- 1,CAST('1/1/' + cast(:year as varchar) AS Date)))\n" +
            "        )\n" +
            "    and u.id=:userId\n" +
            "group by u.id, ad.door_name,ad.door_no",nativeQuery = true)
    List<RoomsForStatistics2> getRoomsForStatistics(@Param("userId") String userId,@Param("year") Integer year, @Param("week") Integer week,@Param("weekday")Integer weekday);


    @Query(value = "select  :userId as id, ad.door_name as room, :weekday as weekday\n" +
            "from acc_monitor_log al\n" +
            "         join acc_door ad on ad.device_id=al.device_id\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    dateadd(\n" +
            "            dd,\n" +
            "            DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),\n" +
            "            0\n" +
            "        )\n" +
            "\n" +
            "    and\n" +
            "    dateadd(\n" +
            "            dd,\n" +
            "            DATEDIFF(dd, DATEPART(dw, GETDATE())-2-:weekday, getdate()),\n" +
            "            0\n" +
            "        )\n" +
            "  and u.id=:userId\n" +
            "group by u.id, ad.door_name",nativeQuery = true)
    List<RoomsForWeekStatistics> getRoomsForStatistics(@Param("userId") String userId,@Param("weekday") Integer weekday);
    //    todo----------------------------------------------------------------------------------------------------------------------------------------


    @Query(value = "select  :userId as id, ad.door_name as room,:week as week,:year as year, :weekday as weekday\n" +
            "from acc_monitor_log al\n" +
            "         join acc_door ad on ad.device_id=al.device_id\n" +
            "         join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    DATEADD(\n" +
            "            DAY,\n" +
            "            + (:weekday-1),\n" +
            "            DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week- 1,CAST('1/1/' + cast(:year as varchar) AS Date)))\n" +
            "        ) and\n" +
            "    DATEADD(\n" +
            "            DAY,\n" +
            "            + (:weekday),\n" +
            "            DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week- 1,CAST('1/1/' + cast(:year as varchar) AS Date)))\n" +
            "        )\n" +
            "  and u.id=:userId\n" +
            "group by u.id, ad.door_name",nativeQuery = true)
    List<RoomsForWeekStatisticsByWeek> getRoomsForStatisticsByWeek(@Param("userId") String userId,@Param("week") Integer week,@Param("year") Integer year,@Param("weekday") Integer weekday);


    @Query(value = "select al.time,\n" +
            "        CASE\n" +
            "            WHEN\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    00,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 1\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    00,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 1\n" +
            "\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    9,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    10,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 2\n" +
            "\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    10,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    11,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 3\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    11,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    12,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 4\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    12,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    13,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 5\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    13,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    14,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 6\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    14,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    15,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 7\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    15,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    16,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 8\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    16,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    17,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 9\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    17,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    18,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 10\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    18,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    19,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 11\n" +
            "\n" +
            "            when\n" +
            "                al.time > DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    19,\n" +
            "                    50,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )\n" +
            "                and\n" +
            "                al.time < DATETIMEFROMPARTS(\n" +
            "                    DATEPART(YEAR, getdate()),\n" +
            "                    DATEPART(MONTH, getdate()),\n" +
            "                    DATEPART(DAY, getdate()),\n" +
            "                    21,\n" +
            "                    00,\n" +
            "                    00,\n" +
            "                    0\n" +
            "                )  THEN 12\n" +
            "\n" +
            "            ELSE 0\n" +
            "            END as section\n" +
            "\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "      join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "     where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and ad.door_name=:room and u.id=:userId",nativeQuery = true)
    List<TimesForRoom> getTimesForRoomStatistics(@Param("userId") String userId, @Param("room") String room);


    @Query(value = "select al.time,\n" +
            "       CASE\n" +
            "           WHEN\n" +
            "                   al.time < DATETIMEFROMPARTS(\n" +
            "                       DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                       DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                       DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                       9,\n" +
            "                       00,\n" +
            "                       00,\n" +
            "                       0\n" +
            "                   )  THEN 1\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           9,\n" +
            "                           00,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               9,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 1\n" +
            "\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           9,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               10,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 2\n" +
            "\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           10,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               11,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 3\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           11,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               12,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 4\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           12,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               13,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 5\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           13,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               14,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 6\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           14,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               15,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 7\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           15,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               16,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 8\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           16,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               17,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 9\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           17,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               18,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 10\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           18,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               19,\n" +
            "                               52,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 11\n" +
            "\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                           19,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                               21,\n" +
            "                               00,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 12\n" +
            "\n" +
            "           ELSE 0\n" +
            "           END as section\n" +
            "\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between dateadd(\n" +
            "        dd,\n" +
            "        DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),\n" +
            "        0\n" +
            "    ) and dateadd(\n" +
            "        dd,\n" +
            "        DATEDIFF(dd, DATEPART(dw, GETDATE())-2-:weekday, getdate()),\n" +
            "        0\n" +
            "    ) and ad.door_name=:room and u.id=:userId",nativeQuery = true)
    List<TimesForRoom> getTimesForRoomStatistics(@Param("userId") String userId, @Param("room") String room,@Param("weekday") Integer weekday);


    @Query(value = "select al.time,\n" +
            "       CASE\n" +
            "           WHEN\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               9,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 1\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           9,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               10,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 2\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           10,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               11,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 3\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           11,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               12,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 4\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           12,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               13,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 5\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           13,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               14,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 6\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           14,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                              DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date)))))," +
            "                               15,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 7\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           15,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               16,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 8\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           16,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               17,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 9\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           17,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               18,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 10\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           18,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               19,\n" +
            "                               50,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 11\n" +
            "           when\n" +
            "                       al.time > DATETIMEFROMPARTS(\n" +
            "                           DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                           19,\n" +
            "                           50,\n" +
            "                           00,\n" +
            "                           0\n" +
            "                       )\n" +
            "                   and\n" +
            "                       al.time < DATETIMEFROMPARTS(\n" +
            "                               DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                               21,\n" +
            "                               00,\n" +
            "                               00,\n" +
            "                               0\n" +
            "                           )  THEN 12\n" +
            "           ELSE 0\n" +
            "           END as section\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id and ad.door_no=al.event_point_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "\n" +
            "    DATEADD(\n" +
            "            DAY,\n" +
            "            + (:weekday-1),\n" +
            "            DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week- 1,CAST('1/1/' + cast(:year as varchar) AS Date)))\n" +
            "        ) and\n" +
            "    DATEADD(\n" +
            "            DAY,\n" +
            "            + (:weekday),\n" +
            "            DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week- 1,CAST('1/1/' + cast(:year as varchar) AS Date)))\n" +
            "        )\n" +
            "\n" +
            "    and ad.door_name=:room and u.id=:userId and ad.door_no=al.event_point_id",nativeQuery = true)
    List<TimesForRoom> getTimesForRoomStatisticsByWeek(@Param("userId") String userId, @Param("room") String room,@Param("week") Integer week,@Param("year") Integer year,@Param("weekday") Integer weekday);


    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section \n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "            DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "            DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "            DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "            8,\n" +
            "            50,\n" +
            "            00,\n" +
            "            0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "          case\n" +
            "              when :section=1 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      9,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=2 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      10,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=3 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      11,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=4 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      12,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=5 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      13,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=6 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      14,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=7 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      15,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=8 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      16,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=9 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      17,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=10 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      18,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=11 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      19,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=12 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      21,\n" +
            "                      00,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              ELSE 0\n" +
            "              END\n" +
            "  and ad.door_name=:room and u.passportNum=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByPassport(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday, @Param("section") Integer section);

    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section \n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "            DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "            DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "            DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "            8,\n" +
            "            50,\n" +
            "            00,\n" +
            "            0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "          case\n" +
            "              when :section=1 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      9,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=2 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      10,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=3 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      11,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=4 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      12,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=5 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      13,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=6 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      14,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=7 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      15,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=8 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      16,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=9 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      17,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=10 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      18,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=11 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      19,\n" +
            "                      54,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              when :section=12 then DATETIMEFROMPARTS(\n" +
            "                      DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                      21,\n" +
            "                      00,\n" +
            "                      00,\n" +
            "                      0)\n" +
            "              ELSE 0\n" +
            "              END\n" +
            "  and ad.door_name=:room and u.login=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByPassportLogin(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday, @Param("section") Integer section);


    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section, ad.door_name as room\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name LIKE :room and u.passportNum=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByPassportByWeek(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday,@Param("week") Integer week,@Param("year") Integer year, @Param("section") Integer section);

    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section, ad.door_name as room\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name LIKE :room and u.login=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByPassportByWeekLogin(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday,@Param("week") Integer week,@Param("year") Integer year, @Param("section") Integer section);

    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section, ad.door_name as room\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name LIKE :room and u.passportNum=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday2> getTimesForRoomStatisticsByPassportByWeek2(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday, @Param("week") Integer week, @Param("year") Integer year, @Param("section") Integer section);

    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section, ad.door_name as room\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name LIKE :room and u.login=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday2> getTimesForRoomStatisticsByPassportByWeek2Login(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday, @Param("week") Integer week, @Param("year") Integer year, @Param("section") Integer section);
//todo-------------------------------------------------------------------------------------------------------------------========================

    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                8,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART(YEAR,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(MONTH,dateadd(dd,DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                DATEPART(DAY, dateadd(dd, DATEDIFF(dd, DATEPART(dw, GETDATE())-1-:weekday, getdate()),0)),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name=:room and u.id=:userId\n",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserId(@Param("userId") String userId, @Param("room") String room, @Param("weekday") Integer weekday, @Param("section") Integer section);

    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name=:room and u.id=:userId",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserIdAndWeek(@Param("userId") String userId, @Param("room") String room, @Param("weekday") Integer weekday,@Param("week") Integer week,@Param("year") Integer year, @Param("section") Integer section);


    @Query(value = "select * from dbo.GetTimesForRoomStatisticsByUserIdAndWeek(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByUserIdAndWeekNEW( String userId,  String room, Integer year,Integer week, Integer weekday,   Integer section);
    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name=:room and u.passportNum=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByPassportAndWeek(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday,@Param("week") Integer week,@Param("year") Integer year, @Param("section") Integer section);


    @Query(value = "select u.id,u.fullName,al.time, :weekday as weekday,  :section as section\n" +
            "from acc_monitor_log al join acc_door ad on ad.device_id=al.device_id\n" +
            "                        join users u on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                8,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                50,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "    and\n" +
            "    case\n" +
            "        when :section=1 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                9,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=2 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                10,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=3 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                11,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=4 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                12,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=5 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                13,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=6 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                14,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=7 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                15,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=8 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                16,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=9 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                17,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=10 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                18,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=11 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                19,\n" +
            "                54,\n" +
            "                00,\n" +
            "                0)\n" +
            "        when :section=12 then DATETIMEFROMPARTS(\n" +
            "                DATEPART( YEAR, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( MONTH, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                DATEPART( DAY, DATEADD( DAY, + (:weekday-1), DATEADD(DAY,-DATEPART(DW,CAST('1/1/' + cast(:year as varchar) AS Date))+2,DATEADD(WK,:week-1,CAST('1/1/' + cast(:year as varchar) AS Date))))),\n" +
            "                21,\n" +
            "                00,\n" +
            "                00,\n" +
            "                0)\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "  and ad.door_name=:room and u.login=:passport",nativeQuery = true)
    List<TeacherStatisticsOfWeekday> getTimesForRoomStatisticsByPassportAndWeekLogin(@Param("passport") String passport, @Param("room") String room, @Param("weekday") Integer weekday,@Param("week") Integer week,@Param("year") Integer year, @Param("section") Integer section);


    @Query(value = "select Top 1 K.nameEn from users u join Teacher T on u.id = T.user_id join Kafedra K on T.kafedra_id = K.id where u.passportNum=:passport",nativeQuery = true)
    String getKafedraIdByUserPassport(@Param("passport") String passport);

    @Query(value = "select Top 1 K.nameEn from users u join Teacher T on u.id = T.user_id join Kafedra K on T.kafedra_id = K.id where u.login=:passport",nativeQuery = true)
    String getKafedraIdByUserPassportLogin(@Param("passport") String passport);

    @Query(value = "select id,fullName,firstName,lastName,middleName,login,RFID,passportNum as passport, gander_id as ganderId from users where (login like :param or RFID like :param or passportNum like :param)",nativeQuery = true)
    List<GetUserForUpdate> getUserForUpdate(@Param("param") String param);

    @Query(value = "select id,fullName,firstName,lastName,middleName,login,RFID,passportNum as passport, gander_id as ganderId from users where id=?1",nativeQuery = true)
   GetUserForUpdate getUserForUpdateById(String id);
}


