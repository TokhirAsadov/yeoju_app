package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StudentMonitoringRestDto {
    String getStudentId();
    String getGroupId();
    String getEducationYearId();
    Integer getYear();
    Integer getWeek();
    Integer getDay();

    @Value("#{@studentRepository.getStudentMonitoringByEducationYear(target.studentId,target.groupId,target.educationYearId)}")
    String getStudentMonitoringByEducationYear();

    @Value("#{@studentRepository.getStudentMonitoringByWeek(target.studentId,target.groupId,target.year,target.week)}")
    String getStudentMonitoringByWeek();

    @Value("#{@studentRepository.getStudentMonitoringByDay(target.studentId,target.groupId,target.year,target.week,target.day)}")
    String getStudentMonitoringByDay();

    @Value("#{@studentRepository.getAllSubjectsByDayAndGroupAndStudentId(target.studentId,target.groupId,target.year,target.week,target.day)}")
    Set<GetAllSubjectsByDayAndGroupAndStudentId> getTimeTableOfToday();
}
