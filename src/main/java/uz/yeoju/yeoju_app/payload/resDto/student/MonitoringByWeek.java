package uz.yeoju.yeoju_app.payload.resDto.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface MonitoringByWeek {
    @JsonIgnore
    String getStudentId();
    @JsonIgnore
    String getGroupId();
    Integer getYear();
    Integer getWeek();

    @Value("#{@studentRepository.getStudentMonitoringByWeek(target.studentId,target.groupId,target.year,target.week)}")
    String getStudentMonitoringByWeek();

    @Value("#{@studentRepository.getDataOfMonitoringByWeek(target.year,target.week,target.studentId,target.groupId)}")
    Set<MonitoringByMonth> getDataOfMonitoringByWeek();

}
