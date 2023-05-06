package uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.monthly;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface MonthlyGroupForDean30 {
    String getId();
    Date getDate();

    @Value("#{@kafedraRepository.getMonthlyStudentsOfGroupForDean30(target.id,target.date)}")
    List<GetTeachersForDekan30> getAll();
}
