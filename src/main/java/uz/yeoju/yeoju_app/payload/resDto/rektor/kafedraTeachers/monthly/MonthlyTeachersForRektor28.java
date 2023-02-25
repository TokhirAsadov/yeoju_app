package uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.monthly;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface MonthlyTeachersForRektor28 {
    String getId();
    Date getDate();

    @Value("#{@kafedraRepository.getMonthlyTeachersOfKafedraForRektor28(target.id,target.date)}")
    List<GetTeachersForDekan28> getAll();
}
