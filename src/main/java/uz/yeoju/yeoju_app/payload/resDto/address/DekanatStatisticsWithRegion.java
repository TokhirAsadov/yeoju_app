package uz.yeoju.yeoju_app.payload.resDto.address;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface DekanatStatisticsWithRegion {
    String getId();

    @Value("#{@addressUserRepository.getDekanatStatisticsByDekanatId(target.id)}")
    List<DStatisticsByDekanatId> getStatistics();

    @Value("#{@addressUserRepository.getFacultiesByDekanatId(target.id)}")
    List<FacultiesForStatistics> getFaculties();
}
