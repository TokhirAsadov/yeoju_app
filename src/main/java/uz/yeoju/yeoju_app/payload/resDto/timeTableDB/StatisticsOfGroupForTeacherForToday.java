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

    @Value("#{@gradeForAttendanceRepository.getValueOfGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getValueOfGradeForAttendance();

    @Value("#{@gradeForAttendanceRepository.getGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getGradeForAttendance();

    @Value("#{@gradeForAttendanceRepository.getMaxGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getMaxGradeForAttendance();

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationId,target.subjectId,target.studentId,target.year,target.week,target.day)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getAllSumGrade(target.studentId,target.educationYearId,target.lessonId)}")
    Double getAllSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getTodayGrade();
}
