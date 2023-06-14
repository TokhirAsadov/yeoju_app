package uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface StudentStatisticsWithWeekOfEduYear {
    String getId();
    String getFacultyId();
    String getGroupsArr();
    String getEduTypeId();
    Integer getSortNumber();
    Integer getWeekNumber();
    Date getStart();
    Date getEnd();

    @Value("#{@dekanRepository.getStudentStatisticsWithWeekOfEduYear(target.start,0,target.facultyId,target.eduTypeId,target.groupsArr)}")
    StudentStatisticsEachOneDay get1();

    @Value("#{@dekanRepository.getStudentStatisticsWithWeekOfEduYear(target.start,1,target.facultyId,target.eduTypeId,target.groupsArr)}")
    StudentStatisticsEachOneDay get2();

    @Value("#{@dekanRepository.getStudentStatisticsWithWeekOfEduYear(target.start,2,target.facultyId,target.eduTypeId,target.groupsArr)}")
    StudentStatisticsEachOneDay get3();

    @Value("#{@dekanRepository.getStudentStatisticsWithWeekOfEduYear(target.start,3,target.facultyId,target.eduTypeId,target.groupsArr)}")
    StudentStatisticsEachOneDay get4();

    @Value("#{@dekanRepository.getStudentStatisticsWithWeekOfEduYear(target.start,4,target.facultyId,target.eduTypeId,target.groupsArr)}")
    StudentStatisticsEachOneDay get5();

}
