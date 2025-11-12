package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetDailyTeacherStatistics;

import java.util.List;

public interface GetKafedraTeacherStatistics {
    String getId();
    String getFullName();
    String getPassport();
    String getLogin();
    String getRFID();
    Double getRate();
    Integer getYear();
    Integer getMonth();

    @Value("#{@dailyTeachersStatisticsRepository.getDailyTeacherStatisticsByMonth(target.id, target.year, target.month)}")
    List<GetDailyTeacherStatistics> getMonthly();
}
