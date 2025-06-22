package uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.dekanat.AcademicRecords;

import java.util.List;

public interface GetAllRecordsOfGroup {
    String getUserId();
    String getLogin();

    @Value("#{@academicRecordsRepository.findAllByQaydRaqami(target.login)}")
    List<AcademicRecords> getRecords();
}
