package uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface MonthlyStaffsForRektor29 {
    String getId();
    Date getDate();

    @Value("#{@sectionRepository.getMonthlyStaffsOfSectionForRektor29(target.id,target.date)}")
    List<GetStaffsForDekan29> getAll();
}
