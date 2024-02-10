package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacherForTodayWithMax {
    String getEducationId();
    String getSubjectId();
    String getGroupId();
    String getTeacherId();

    String getYear();
    Integer getWeek();
    Integer getDay();

    @Value("#{@groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(target.educationId,target.groupId,target.subjectId,target.teacherId,target.year,target.week,target.day)}")
    Set<StatisticsOfGroupForTeacherForToday> getStudents();
//    @Value("#{@groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(target.educationId,target.groupId,target.subjectId,target.teacherId)}")
//    Set<StatisticsOfGroupForTeacher> getStudents();
}
