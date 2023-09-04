package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacher {
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

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationId,target.subjectId,target.studentId)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    @Value("#{@gradeOfStudentByTeacherRepository.getMiddleGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getMiddleGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getTodayGrade();
}
