package uz.yeoju.yeoju_app.payload.resDto.rektor.staff;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard.TeacherComeStatistics;

public interface StaffStatistics {

    String getId();
    String getName();

    @Value("#{@kafedraRepository.getStaffComeCountForRektor(target.id)}")
    TeacherComeStatistics getData();
}
