package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface FacultyStatisticsWithWeekOrMonth {
    String getId();
    String getSchoolName();
    Integer getSchoolCode();

    Integer getLevel();
    Integer getWeekOrMonth();

    @Value("#{@studentRepository.studentFaculty123231213ByWeekOrMonthBySchoolCode123(target.schoolCode,target.level,target.weekOrMonth)}")
    List<FacultyStatistic> getAllData();
}
