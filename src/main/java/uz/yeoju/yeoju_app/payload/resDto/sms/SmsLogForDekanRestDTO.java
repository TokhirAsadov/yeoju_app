package uz.yeoju.yeoju_app.payload.resDto.sms;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.enums.SmsStatus;
import uz.yeoju.yeoju_app.entity.enums.SmsType;
import uz.yeoju.yeoju_app.payload.resDto.user.UserResDto;

import java.sql.Timestamp;

public interface SmsLogForDekanRestDTO {
    String getId();
    Timestamp getCreatedAt();
    String getCreatedBy();
    String getMessageBody();
    String getGroupName();
    Integer getCourse();
    String getStatus();
    String getSmsType();

    @Value("#{@userRepository.getUserFieldsForSMS(target.id)}")
    UserResDto getUser();
}
