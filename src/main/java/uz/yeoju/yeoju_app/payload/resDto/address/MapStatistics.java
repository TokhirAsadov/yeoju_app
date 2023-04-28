package uz.yeoju.yeoju_app.payload.resDto.address;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface MapStatistics {
    String getRegion();
    Integer getCount();

    @Value("#{@addressUserRepository.getGandersStatistics(target.region)}")
    List<GanderStatistics> getGanders();

    @Value("#{@addressUserRepository.getEduTypeStatistics(target.region)}")
    List<EduTypeStatistics> getEduTypes();
}
