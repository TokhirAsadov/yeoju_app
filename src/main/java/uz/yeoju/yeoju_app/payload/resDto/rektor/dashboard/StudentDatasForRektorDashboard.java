package uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentAddress;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentGroupField;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentPhones;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentResults;

import java.util.Date;
import java.util.List;

public interface StudentDatasForRektorDashboard {
    String getId();
    String getLogin();
    String getFullName();
    Date getBornYear();
    String getCitizenship();
    String getNationality();
    String getPassportNum();

    @Value("#{@groupRepository.getGroupFieldByUserId(target.id)}")
    StudentGroupField getGroupData();
}
