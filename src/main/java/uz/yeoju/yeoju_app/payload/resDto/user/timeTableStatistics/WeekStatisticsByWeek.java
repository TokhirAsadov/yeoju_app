package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface WeekStatisticsByWeek {

    String getId();
    String getFullName();
    Integer getWeek();
    Integer getYear();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,1)}")
    List<RoomsForWeekStatisticsByWeek> get1();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,2)}")
    List<RoomsForWeekStatisticsByWeek> get2();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,3)}")
    List<RoomsForWeekStatisticsByWeek> get3();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,4)}")
    List<RoomsForWeekStatisticsByWeek> get4();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,5)}")
    List<RoomsForWeekStatisticsByWeek> get5();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,6)}")
    List<RoomsForWeekStatisticsByWeek> get6();

    @Value("#{@userRepository.getRoomsForStatisticsByWeek(target.id,target.week,target.year,7)}")
    List<RoomsForWeekStatisticsByWeek> get7();


}
