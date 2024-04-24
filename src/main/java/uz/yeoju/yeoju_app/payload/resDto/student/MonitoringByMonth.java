package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

public interface MonitoringByMonth {
    String getStudentId();
    String getGroupId();
    Integer getYear();
    Integer getWeek();
    Integer getDay();
    Integer getWeekDay();
    Date getDate();

    @Value("#{@studentRepository.getStudentMonitoringByDay(target.studentId,target.groupId,target.year,target.week,target.weekDay)}")
    String getStudentMonitoringByDay();

    @Value("#{@studentRepository.getAllSubjectsByDayAndGroupAndStudentId(target.studentId,target.groupId,target.year,target.week,target.weekDay)}")
    Set<GetAllSubjectsByDayAndGroupAndStudentId> getTimeTableOfToday();

}
