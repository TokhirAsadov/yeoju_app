package uz.yeoju.yeoju_app.payload.resDto.rektor.journal;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface StudentsOfGroupWithTodayStatisticsAndScore {
    String getId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getFullName();
    String getLogin();
    String getPassport();
    String getRfid();

    String getEducationYearId(); // for to get score

    @Value("#{@groupRepository.getFirstOfEnteringNEW(target.rfid)}")
    Date getEntering();
}
