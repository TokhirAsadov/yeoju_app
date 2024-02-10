package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacherForTodayWithMax2 {
    String getEducationId();
    String getSubjectId();
    String getGroupId();
    String getTeacherId();

    @Value("#{@groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(target.educationId,target.groupId,target.subjectId,target.teacherId)}")
    Set<StatisticsOfGroupForTeacher> getStudents();

}
