package uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentGroupField;

import java.util.Date;

public interface StaffDatasForRektorDashboard {
    String getId();
    String getLogin();
    String getFullName();
    Date getBornYear();
    String getCitizenship();
    String getNationality();
    String getPassportNum();

    String getRoleName();
}
