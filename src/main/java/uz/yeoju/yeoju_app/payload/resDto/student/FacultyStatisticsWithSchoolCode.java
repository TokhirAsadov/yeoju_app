package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface FacultyStatisticsWithSchoolCode {
    String getId();
    String getEduType();
    String getSchoolName();
    Integer getSchoolCode();

    Integer getLevel();
    Date getDateFrom();
    Date getDateTo();

    @Value("#{@studentRepository.getFacultyAndComingCountWithAllByGroupLevelWithSchoolCode(target.schoolCode,target.level,target.eduType,target.dateFrom,target.dateTo)}")
    List<FacultyStatistic> getAllData();
}
