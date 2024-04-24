package uz.yeoju.yeoju_app.payload.resDto.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface MonitoringByMonth {
    @JsonIgnore
    String getStudentId();
    @JsonIgnore
    String getGroupId();
    Integer getYear();
    Integer getWeek();
    Integer getDay();
    Integer getWeekDay();
    Date getDate();

    @Value("#{@studentRepository.getStudentMonitoringByDay(target.studentId,target.groupId,target.year,target.week,target.weekDay)}")
    String getStudentMonitoringByDay();

//    @Value("#{@studentRepository.getAllSubjectsByDayAndGroupAndStudentId(target.studentId,target.groupId,target.year,target.week,target.weekDay)}")
//    Set<GetAllSubjectsByDayAndGroupAndStudentId> getTimeTableOfToday();

}
