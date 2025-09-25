package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface GetAllKafedraTeachersStatistics {
    String getDayInWeek();

    @Value("#{@kafedraTeachersStatisticsRepository.findStatisticsByDay(target.dayInWeek)}")
    List<GetKafedraStatistics> getKafedras();
}
