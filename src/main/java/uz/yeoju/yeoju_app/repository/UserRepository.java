package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentData;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.KafedraStatistics;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.RektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StaffDatasForRektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.StudentDatasForRektorDashboard;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers28;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers29;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers31;
import uz.yeoju.yeoju_app.payload.resDto.search.SearchDto;
import uz.yeoju.yeoju_app.payload.resDto.user.UserField;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSave;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;
import uz.yeoju.yeoju_app.payload.resDto.user.UserResDto;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLoginOrEmailOrRFID(String login, String email, String RFID);
    User getUserByLogin(String login);
    User findUserByEmail(String email);
    User findUserByRFID(String RFID);

    boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers31 getKafedraTeachersDataForRektorByKafedraId31(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers30 getKafedraTeachersDataForRektorByKafedraId30(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers29 getKafedraTeachersDataForRektorByKafedraId29(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    KafedraTeachers28 getKafedraTeachersDataForRektorByKafedraId28(@Param("id") String id);

    @Query(value = "select id,nameEn as name from Kafedra",nativeQuery = true)
    List<KafedraStatistics> getKafedraStatisticsForRektor();

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

    @Query(value = "select u.id as userId, u.fullName,u.login,u.passportNum as passport,r.roleName from users u\n" +
            "left join Student S on u.id = S.user_id\n" +
            "         join users_Role uR on u.id = uR.users_id\n" +
            "         join Role r on uR.roles_id = r.id\n" +
            "where login like :keyword or fullName like :keyword\n" +
            "or S.passportSerial like :keyword",nativeQuery = true)
    List<SearchDto> getUserForSearch(@Param("keyword") String keyword);

    User getUserByPassportNum(String passportNum);

    @Query(value = "select id,login,fullName,bornYear,citizenship,nationality,passportNum from users where id=:userId",nativeQuery = true)
    StudentData getStudentDataByUserId(@Param("userId") String userId);


    @Query(value = "select u.id,u.login,u.fullName,u.bornYear,u.citizenship,u.nationality,u.passportNum,u.email, t.workerStatus  from users u join Teacher t on u.id = t.user_id where u.id=:userId",nativeQuery = true)
    TeacherData getTeacherDataByUserId(@Param("userId") String userId);


    @Query(value = "select id,fullName,email,bornYear,citizenship,nationality,passportNum from users where id=:userId",nativeQuery = true)
    UserField getUserFields(@Param("userId") String userId);

    @Query(value = "select TOP 50 id as value,fullName as label from users order by fullName asc",nativeQuery = true)
    List<UserForTeacherSaveItem> getUserForTeacherSaving();

    @Query(value = "select id from users where id=:id",nativeQuery = true)
    UserForTeacherSave getItemsForTeacherSaving(@Param("id") String id);

    @Query(value = "select id as value,fullName as label from users where fullName like :keyword order by fullName asc",nativeQuery = true)
    List<UserForTeacherSaveItem> getUserForTeacherSavingSearch(@Param("keyword") String keyword);


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
            "               where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.passportNum=:pass\n" +
            "              ) as card\n" +
            "                  join users u on cast(card.cardNo as varchar) =\n" +
            "                                  cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                  join Teacher t on u.id = t.user_id\n" +
            "                  join Kafedra K on t.kafedra_id = K.id\n" +
            "                  join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "          where KM.user_id=:kafedraId and u.passportNum=:pass\n" +
            "          group by t.kafedra_id,t.user_id\n" +
            "      ) as f1\n" +
            "          right join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "   join users u2 on t.user_id = u2.id\n" +
            "   join Kafedra K on t.kafedra_id = K.id\n" +
            "   join KafedraMudiri KM on K.id = KM.kafedra_id\n" +
            "    where KM.user_id=:kafedraId and u2.passportNum=:pass\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id where f1.user_id is null",nativeQuery = true)
    TeacherData getTeachersForRemember(@Param("pass") String pass,@Param("kafedraId") String kafedraId);

//    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
//            " select t.kafedra_id,t.user_id from\n" +
//            "     (select  al.card_no as cardNo\n" +
//            "      from acc_monitor_log al\n" +
//            "               join users u\n" +
//            "                    on cast(u.RFID as varchar) =\n" +
//            "                       cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "               join users_Role ur\n" +
//            "                    on u.id = ur.users_id\n" +
//            "               join (select id from Role where roleName = 'ROLE_TEACHER') as role\n" +
//            "                    on ur.roles_id = role.id\n" +
//            "      where al.time between DATEADD(dd, DATEDIFF(dd, 0, getdate()), 0) and DATEADD(dd, DATEDIFF(dd, -1, getdate()), 0) and u.passportNum=:pass\n" +
//            "     ) as card\n" +
//            "         join users u on cast(card.cardNo as varchar) =\n" +
//            "                         cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
//            "         join Teacher t on u.id = t.user_id\n" +
//            " where t.kafedra_id=:kafedraId\n" +
//            " group by t.kafedra_id,t.user_id\n" +
//            ") as f1\n" +
//            "right join (\n" +
//            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
//            "                                  join users u2 on t.user_id = u2.id\n" +
//            "    where t.kafedra_id=:kafedraId and u2.passportNum=:pass\n" +
//            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
//            ") as f2 on f2.user_id = f1.user_id where f1.user_id is null",nativeQuery = true)
//    TeacherData getTeachersForRemember(@Param("pass") String pass,@Param("kafedraId") String kafedraId);
}
