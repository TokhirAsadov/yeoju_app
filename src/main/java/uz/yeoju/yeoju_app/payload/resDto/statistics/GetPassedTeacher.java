package uz.yeoju.yeoju_app.payload.resDto.statistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface GetPassedTeacher {
    String getKafedra();
    String getFullName();
    String getLogin();
    String getClassroom();
    String getCardId();
    String getRfid();
    Integer getSection();

//    @Value("#{@cardDbStatisticsRepository.getEarliestTime(target.cardId,target.rfid)}")
    Date getTime();

}
