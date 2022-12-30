package uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard;

import org.springframework.beans.factory.annotation.Value;

public interface KafedraStatistics {
    String getId();
    String getName();

    @Value("#{@kafedraRepository.getComeCountForRektor(target.id)}")
    TeacherComeStatistics getData();
}
