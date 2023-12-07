package uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

public interface StudentStatisticsEachOneDay {
    Date getTime();
    String getFacultyId();
    String getEduTypeId();
    String getGroupsArr();

    @Value("#{@dekanRepository.getStudentDataByWeekDayNEW(target.time,target.groupsArr)}")
    Set<StudentDataByWeekDay> getStatistics();
}
