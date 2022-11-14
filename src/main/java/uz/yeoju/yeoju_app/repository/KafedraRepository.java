package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.payload.resDto.dekan.SearchUserForDekanUseSendMessage;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.*;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.*;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.Date;
import java.util.List;

public interface KafedraRepository extends JpaRepository<Kafedra, String> {
    Kafedra getKafedraByNameEn(String name);
    boolean existsKafedraByNameEn(String name);

    @Query(value = "select id as value, nameEn as label from Kafedra order by nameEn",nativeQuery = true)
    List<UserForTeacherSaveItem> getKafedraForTeacherSaving();

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =(select km.kafedra_id as id from KafedraMudiri km where user_id=:id)",nativeQuery = true)
    List<GetTeachersOfKafedra31> getTeachersOfKafedra31(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =(select km.kafedra_id as id from KafedraMudiri km where user_id=:id)",nativeQuery = true)
    List<GetTeachersOfKafedra30> getTeachersOfKafedra30(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =(select km.kafedra_id as id from KafedraMudiri km where user_id=:id)",nativeQuery = true)
    List<GetTeachersOfKafedra29> getTeachersOfKafedra29(@Param("id") String id);

    @Query(value = "select u.fullName,u.id,u.passportNum as passport,u.login,u.RFID,u.email from Teacher T\n" +
            "    join users u on T.user_id = u.id\n" +
            "where T.kafedra_id =(select km.kafedra_id as id from KafedraMudiri km where user_id=:id)",nativeQuery = true)
    List<GetTeachersOfKafedra28> getTeachersOfKafedra28(@Param("id") String id);

    @Query(value = "select id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date from users where id=:id",nativeQuery = true)
    GetDate31 getDate31(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select id, :date as date from users where id=:id",nativeQuery = true)
    GetDate30 getDate30(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select id, :date as date from users where id=:id",nativeQuery = true)
    GetDate29 getDate29(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select id, :date as date from users where id=:id",nativeQuery = true)
    GetDate28 getDate28(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select k.id, k.nameEn as name from Kafedra k join KafedraMudiri km on km.kafedra_id=k.id where km.user_id=:userId",nativeQuery = true)
    KafedraResDto getKafedraNameByUserId(@Param("userId") String userId);


    @Query(value = "select  :kafedraId as kafedraId,f1.count as comeCount,f2.count as allCount from (\n" +
            "  select t.kafedra_id,count(card.cardNo) as count from\n" +
            "      (select  al.card_no as cardNo\n" +
            "       from acc_monitor_log al\n" +
            "                join users u\n" +
            "                     on cast(u.RFID as varchar) =\n" +
            "                        cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                join users_Role ur\n" +
            "                     on u.id = ur.users_id\n" +
            "                join (select id from Role where roleName = 'Teacher') as role\n" +
            "                     on ur.roles_id = role.id\n" +
            "       where al.time between getdate() and DATEADD(d, 1, getdate())\n" +
            "       group by al.card_no\n" +
            "      ) as card\n" +
            "          join users u on cast(card.cardNo as varchar) =\n" +
            "                          cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "          join Teacher t on u.id = t.user_id\n" +
            "  where t.kafedra_id=:kafedraId\n" +
            "  group by t.kafedra_id\n" +
            ") as f1\n" +
            "  right join (\n" +
            "    select t.kafedra_id,count(t.id) as count from Teacher t\n" +
            "    where t.kafedra_id=:kafedraId\n" +
            "    group by t.kafedra_id\n" +
            ") as f2 on f2.kafedra_id = f1.kafedra_id",nativeQuery = true)
    ComeCountTodayStatistics getComeCountTodayStatistics(@Param("kafedraId") String id);


    @Query(value = "select f1.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport from (\n" +
            " select t.kafedra_id,t.user_id from\n" +
            "     (select  al.card_no as cardNo\n" +
            "      from acc_monitor_log al\n" +
            "               join users u\n" +
            "                    on cast(u.RFID as varchar) =\n" +
            "                       cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "               join users_Role ur\n" +
            "                    on u.id = ur.users_id\n" +
            "               join (select id from Role where roleName = 'Teacher') as role\n" +
            "                    on ur.roles_id = role.id\n" +
            "      where al.time between getdate() and DATEADD(d, 1, getdate())\n" +
            "     ) as card\n" +
            "         join users u on cast(card.cardNo as varchar) =\n" +
            "                         cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join Teacher t on u.id = t.user_id\n" +
            " where t.kafedra_id=:kafedraId\n" +
            " group by t.kafedra_id,t.user_id\n" +
            ") as f1\n" +
            " join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "                                  join users u2 on t.user_id = u2.id\n" +
            "    where t.kafedra_id=:kafedraId\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login, u2.passportNum\n" +
            ") as f2 on f2.user_id = f1.user_id",nativeQuery = true)
    List<ComeStatistics> getStatisticsForKafedraDashboardCome(@Param("kafedraId") String kafedraId);

    @Query(value = "select f2.user_id as id,f2.fullName,f2.email,f2.RFID,f2.login,f2.passportNum as passport  from (\n" +
            "     select t.kafedra_id,t.user_id from\n" +
            "         (select  al.card_no as cardNo\n" +
            "          from acc_monitor_log al\n" +
            "                   join users u\n" +
            "                        on cast(u.RFID as varchar) =\n" +
            "                           cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "                   join users_Role ur\n" +
            "                        on u.id = ur.users_id\n" +
            "                   join (select id from Role where roleName = 'Teacher') as role\n" +
            "                        on ur.roles_id = role.id\n" +
            "          where al.time between getdate() and DATEADD(d, 1, getdate())\n" +
            "         ) as card\n" +
            "             join users u on cast(card.cardNo as varchar) =\n" +
            "                             cast(u.RFID as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "             join Teacher t on u.id = t.user_id\n" +
            "     where t.kafedra_id=:kafedraId\n" +
            "     group by t.kafedra_id,t.user_id\n" +
            " ) as f1\n" +
            "    right join (\n" +
            "    select t.kafedra_id,t.user_id,u2.fullName, u2.email, u2.RFID, u2.login,u2.passportNum from Teacher t\n" +
            "    join users u2 on t.user_id = u2.id where t.kafedra_id=:kafedraId\n" +
            "    group by t.kafedra_id,t.user_id,u2.fullName, t.user_id, t.kafedra_id, u2.email, u2.RFID, u2.login,u2.passportNum\n" +
            ") as f2 on f2.kafedra_id = f1.kafedra_id where f1.kafedra_id is null",nativeQuery = true)
    List<NoComeStatistics> getStatisticsForKafedraDashboardNoCome(@Param("kafedraId") String kafedraId);

    @Query(value = "select Top 1 al.time from acc_monitor_log al join users u on cast(u.RFID as varchar) =cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "where al.time between getdate() and DATEADD(d, 1, getdate()) and u.id=:id order by al.time asc\n",nativeQuery = true)
    Date getEnterTime(@Param("id") String id);

    @Query(value = "select u.id,u.fullName from users u join Teacher t on u.id = t.user_id where t.kafedra_id=:kafedraId and (u.fullName like :searchParam or u.passportNum like :searchParam or u.login like :searchParam or u.RFID like :searchParam)",nativeQuery = true)
    List<SearchUserForDekanUseSendMessage> getTeachersForSendSms(@Param("kafedraId") String kafedraId, @Param("searchParam") String searchParam);
}
