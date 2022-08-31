package uz.yeoju.yeoju_app.entity.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.enums.SmsStatus;
import uz.yeoju.yeoju_app.entity.enums.SmsType;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SmsLog extends AbsEntity {

    private String messageBody;

//    private List<String> recipientsPhoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SmsStatus status = SmsStatus.SENDING;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SmsType smsType;

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private SmsByWhom whom;

    private int attempt = 0; // urunishlar soni, statusi FAILED bulganlariga junatilgan sms lar soni, agar urunishlar soni 3 tadan ortsa sms junatiw tuxtatiladi

    private String groupName;
    private String userId;
    private Integer course;

    public SmsLog(String id, String messageBody,/* List<String> recipientsPhoneNumber,*/ SmsStatus status, SmsType smsType, String groupName, Integer course) {
        super(id);
        this.messageBody = messageBody;
//        this.recipientsPhoneNumber = recipientsPhoneNumber;
        this.status = status;
        this.smsType = smsType;
        this.groupName = groupName;
        this.course = course;
    }
}
