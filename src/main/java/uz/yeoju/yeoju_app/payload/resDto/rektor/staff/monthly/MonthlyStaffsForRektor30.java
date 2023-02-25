package uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface MonthlyStaffsForRektor30 {
    String getId();
    Date getDate();

    @Value("#{@sectionRepository.getMonthlyStaffsOfSectionForRektor30(target.id,target.date)}")
    List<GetStaffsForDekan30> getAll();
}
