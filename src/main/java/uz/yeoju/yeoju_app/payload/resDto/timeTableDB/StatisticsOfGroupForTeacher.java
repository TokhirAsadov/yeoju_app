package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacher {
    String getEducationId();
    String getSubjectId();
    String getGroupId();

    String getTeacherId();
    String getStudentId();
    @JsonIgnore
    String getFirstName();
    @JsonIgnore
    String getLastName();
    @JsonIgnore
    String getMiddleName();
    String getFullName();
    String getUserId(); // login

    @Value("#{@gradeForAttendanceRepository.getValueOfGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getGradeForAttendance();

//    @Value("#{@gradeForAttendanceRepository.getGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
//    Float getGradeForAttendance();

    @Value("#{@gradeForAttendanceRepository.getMaxGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getMaxGradeForAttendance();

//    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationId,target.subjectId,target.studentId)}")
//    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

     @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentIdNEW(target.studentId,target.groupId,target.educationId,target.subjectId)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    @Value("#{@gradeForAttendanceRepository.getAllGradesForAttendance(target.studentId,target.groupId,target.educationId,target.subjectId)}")
    Float getAllGradesForAttendance();


    //    @Value("#{@gradeOfStudentByTeacherRepository.getMiddleGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
//    Double getMiddleGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getAllSumGrade(target.studentId,target.educationId,target.subjectId)}")
    Double getAllSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getTodayGrade();
}
