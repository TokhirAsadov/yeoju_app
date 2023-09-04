package uz.yeoju.yeoju_app.payload.resDto.notification;

import java.sql.Timestamp;

public interface NotificationResDto {
//    it will use
    String getId();
    String getContent();
    String getType();
    Timestamp getCreatedAt();
    Timestamp getUpdatedAt();
    String getCreatedBy();
    String getUpdatedBy();
    String getUserFromId();
    String getUserToId();

}
