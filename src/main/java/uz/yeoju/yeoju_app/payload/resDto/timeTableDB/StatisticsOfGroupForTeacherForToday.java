package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacherForToday {
    String getEducationId();
    String getSubjectId();
    String getGroupId();

    String getTeacherId();
    String getStudentId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getFullName();
    String getUserId(); // login

    String getYear();
    Integer getWeek();
    Integer getDay();

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationId,target.subjectId,target.studentId,target.year,target.week,target.day)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    @Value("#{@gradeOfStudentByTeacherRepository.getMiddleGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getMiddleGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getTodayGrade();
}
