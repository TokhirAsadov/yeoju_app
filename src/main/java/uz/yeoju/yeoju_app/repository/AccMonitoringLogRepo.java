package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.AccMonitorLog;
import uz.yeoju.yeoju_app.payload.resDto.user.GetUsersByRoleNameAndBetweenDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AccMonitoringLogRepo extends JpaRepository<AccMonitorLog, Long> {



   @Query("select a from acc_monitor_log a where a.card_no = ?1")
   List<AccMonitorLog> findAccMonitorLogsByCard_no(String cardNumber);

    @Query(value = "select * from acc_monitor_log m where m.time between :dateFrom and :dateTo", nativeQuery = true)
    List<AccMonitorLog> findMonitoring(@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo")LocalDateTime dateTo);

    @Query(value = "select card_no as card_no,COUNT(card_no) as count from acc_monitor_log m group by m.card_no", nativeQuery = true)
    List<Map<String,String>> groupby();

    List<AccMonitorLog> findAccMonitorLogsByTimeBetween(LocalDateTime time, LocalDateTime time2);

    List<AccMonitorLog> findAccMonitorLogsByTimeBefore(LocalDateTime time);

    @Query(value = "  select m.card_no\n" +
            "                     from acc_monitor_log m\n" +
            "                     where m.card_no != '0'\n" +
            "                       and m.time between :dateFrom and :dateTo\n" +
            "                     group by m.card_no",nativeQuery = true)
    List<String> getCardIdsBetweenDate(@Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo")LocalDateTime dateTo);

    @Query(value = "select u.fullName, count(al.id) as countOfTouch, al.card_no as cardNumber\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where id=:roleId) as role\n" +
            "              on ur.roles_id = role.id\n" +
            "            where al.time between :dateFrom and :dateTo\n" +
            "         group by al.card_no, u.fullName", nativeQuery = true)
    List<GetUsersByRoleNameAndBetweenDate> getUsersByRoleIdAndBetweenDate(@Param("roleId") String roleId, @Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo")LocalDateTime dateTo);

    @Query(value = "select count(*) from ( select u.fullName, count(al.id) as countOfTouch, al.card_no as cardNumber\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where id=:roleId) as role\n" +
            "              on ur.roles_id = role.id\n" +
            "            where al.time between :dateFrom and :dateTo\n" +
            "         group by al.card_no, u.fullName) as counter", nativeQuery = true)
    Long countUsersByRoleIdAndBetweenDate(@Param("roleId") String roleId, @Param("dateFrom")LocalDateTime dateFrom, @Param("dateTo")LocalDateTime dateTo);

    @Query(value = "select count(*) from ( select u.fullName, count(al.id) as countOfTouch, al.card_no as cardNumber\n" +
            "from acc_monitor_log al\n" +
            "         join users u\n" +
            "              on cast(u.RFID as varchar) = cast(al.card_no as varchar) COLLATE Chinese_PRC_CI_AS\n" +
            "         join users_Role ur\n" +
            "              on u.id = ur.users_id\n" +
            "         join (select id from Role where id=:roleId) as role\n" +
            "              on ur.roles_id = role.id\n" +
            "            where al.time between dateadd(d,:weekOrMonth,convert(DATE,GETDATE())) and dateadd(d,1,convert(DATE,GETDATE()))\n" +
            "         group by al.card_no, u.fullName) as counter", nativeQuery = true)
    Long countUsersByRoleIdAndWeekOrMonth(@Param("roleId") String roleId, @Param("weekOrMonth") Integer weekOrMonth);


}
