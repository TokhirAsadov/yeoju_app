package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.AccMonitorLog;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccMonitoringLogRepo extends JpaRepository<AccMonitorLog, Long> {

   @Query("select a from acc_monitor_log a where a.card_no = ?1")
   List<AccMonitorLog> findAccMonitorLogsByCard_no(String cardNumber);

    @Query(value = "select * from acc_monitor_log w where w.time < :date;", nativeQuery=true)
    List<AccMonitorLog> findBeforeDate(@Param("date") Date date);



    @Query(value = "SELECT * FROM acc_monitor_log " +
            "where date_time between cast(:dateFrom AS timestamp) AND cast(:dateTo AS timestamp)", nativeQuery = true)
    List<AccMonitorLog> findAllEventsBetweenDate(@Param("dateTo")String dateTo, @Param("dateFrom")String dateFrom);


    List<AccMonitorLog> findAccMonitorLogsByTimeBetween(LocalDateTime time, LocalDateTime time2);
    List<AccMonitorLog> findAccMonitorLogsByTimeBefore(LocalDateTime time);

}
