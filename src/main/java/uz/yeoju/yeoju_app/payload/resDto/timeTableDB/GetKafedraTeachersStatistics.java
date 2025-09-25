package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;


public interface GetKafedraTeachersStatistics {
    String getDayInWeek();
    @JsonIgnore
    String getKafedraId();

    @Value("#{@kafedraTeachersStatisticsRepository.findStatisticsByKafedraIdAndDay(target.kafedraId,target.dayInWeek)}")
    GetKafedraStatistics getKafedra();
}
