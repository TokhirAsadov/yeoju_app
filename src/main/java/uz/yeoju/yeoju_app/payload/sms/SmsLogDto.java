package uz.yeoju.yeoju_app.payload.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.SmsStatus;
import uz.yeoju.yeoju_app.entity.enums.SmsType;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsLogDto {
    private String id;
    private String messageBody;
//    private List<String> recipientsPhoneNumber;
    private SmsStatus status;
    private SmsType smsType;
    private String groupName;
    private String userId;
    private Integer course;
    private int attempt;
}
