package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.enums.SmsStatus;
import uz.yeoju.yeoju_app.entity.sms.SmsLog;
import uz.yeoju.yeoju_app.payload.resDto.sms.SmsLogForDekanRestDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SmsLogRepository extends JpaRepository<SmsLog, String> {

    @Query(value = "select TOP 1 * from SmsLog where status= :status",nativeQuery = true)
    SmsLog getSmsByStatusUseSending(@Param ("status") String status);

    @Query(value = "select id,createdAt,createdBy,messageBody,groupName,course,status,smsType from SmsLog where createdBy = :ownerId order by createdAt desc",nativeQuery = true)
    List<SmsLogForDekanRestDTO> getHistoryOfSMSForDekan(@Param("ownerId") String ownerId);
}
