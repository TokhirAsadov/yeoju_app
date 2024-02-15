package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.StudentsDynamicAttendance;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    Float getValueOfGradeForAttendance();

    @Value("#{@gradeForAttendanceRepository.getGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getGradeForAttendance();

    @Value("#{@gradeForAttendanceRepository.getMaxGradeForAttendance(target.groupId,target.educationId,target.subjectId)}")
    Float getMaxGradeForAttendance();

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationId,target.subjectId,target.studentId)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    default Float getAllGradesForAttendance(){
        if (this.getGradeForAttendance() == null ||this.getGradeForAttendance()==0){
            return (float) 0;
        }
        AtomicInteger counter = new AtomicInteger();
        this.getSubjects().forEach(subject ->{
            if (!subject.getStatistics().isEmpty()) {
                Set<StudentsDynamicAttendance> dynamic = subject.getStatistics().stream().filter(stats -> stats.getType().equalsIgnoreCase("DYNAMIC") && !stats.getIsCome()).collect(Collectors.toSet());
                if (dynamic.isEmpty()) {
                    counter.getAndIncrement();
                }
            }
        });
        return counter.get()*this.getGradeForAttendance();
    };

//    @Value("#{@gradeOfStudentByTeacherRepository.getMiddleGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
//    Double getMiddleGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getAllSumGrade(target.studentId,target.educationId,target.subjectId)}")
    Double getAllSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationId,target.subjectId)}")
    Double getTodayGrade();
}
