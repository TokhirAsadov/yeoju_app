package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForTeacherForToday {
//    @JsonIgnore
    String getEducationId();
//    @JsonIgnore
    String getSubjectId();
//    @JsonIgnore
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

    @JsonIgnore
    String getYear();
    @JsonIgnore
    Integer getWeek();
    @JsonIgnore
    Integer getDay();

    @Value("#{@gradeForAttendanceRepository.getValueOfGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getGradeForAttendance();

//    @Value("#{@gradeForAttendanceRepository.getGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
//    Float getGradeForAttendance();

    @Value("#{@gradeForAttendanceRepository.getMaxGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getMaxGradeForAttendance();

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationId,target.subjectId,target.studentId,target.year,target.week,target.day)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    @Value("#{@gradeForAttendanceRepository.getAllGradesForAttendance(target.studentId,target.groupId,target.educationId,target.subjectId)}")
    Float getAllGradesForAttendance();

    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getAllSumGrade(target.studentId,target.educationId,target.subjectId)}")
    Double getAllSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getTodayGrade();
}
