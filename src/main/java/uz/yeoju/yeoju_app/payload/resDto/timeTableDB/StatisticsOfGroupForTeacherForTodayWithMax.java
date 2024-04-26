package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacherForTodayWithMax {
    @JsonIgnore
    String getEducationId();
    @JsonIgnore
    String getSubjectId();
    @JsonIgnore
    String getGroupId();
    @JsonIgnore
    String getTeacherId();

    String getYear();
    Integer getWeek();
    Integer getDay();

    @Value("#{@groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(target.educationId,target.groupId,target.subjectId,target.teacherId,target.year,target.week,target.day)}")
    Set<StatisticsOfGroupForTeacherForToday> getStudents();
}
