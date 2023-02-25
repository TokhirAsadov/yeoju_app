package uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface WeekStatistics {

    String getId();
    String getFullName();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,1)}")
    List<RoomsForWeekStatistics> get1();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,2)}")
    List<RoomsForWeekStatistics> get2();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,3)}")
    List<RoomsForWeekStatistics> get3();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,4)}")
    List<RoomsForWeekStatistics> get4();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,5)}")
    List<RoomsForWeekStatistics> get5();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,6)}")
    List<RoomsForWeekStatistics> get6();

    @Value("#{@userRepository.getRoomsForStatistics(target.id,7)}")
    List<RoomsForWeekStatistics> get7();


}
