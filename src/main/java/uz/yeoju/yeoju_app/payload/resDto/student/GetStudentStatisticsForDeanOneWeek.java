package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetStudentStatisticsForDeanOneWeek {
    String getGroupId();
    String getStudentId();
    String getFullName();
    String getEducationYearId();
    Integer getWeekday();
    Integer getWeek();
    Integer getYear();

    @Value("#{@groupRepository.getStudentStatisticsForDeanOneWeekSection(target.educationYearId, target.groupId, target.weekday, target.week, target.year,target.studentId)}")
    Set<GetStudentStatisticsForDeanOneWeekSection> getSubjects();
}