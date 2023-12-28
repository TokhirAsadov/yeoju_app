package uz.yeoju.yeoju_app.payload.resDto.dekan.queue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentData;

import java.sql.Timestamp;

public interface GetQueueRestDto {
    String getId();
    Long getNumber();
    Timestamp getCreatedAt();
    String getOwnerId();
    Timestamp getWaitingAt();
    Timestamp getStartedAt();
    Timestamp getCalledAt();
    Timestamp getFinishedAt();
    String getStatus();

    @Value("#{@userRepository.getStudentDataByUserIdNEW(target.ownerId)}")
    StudentData getStudentData();
}
