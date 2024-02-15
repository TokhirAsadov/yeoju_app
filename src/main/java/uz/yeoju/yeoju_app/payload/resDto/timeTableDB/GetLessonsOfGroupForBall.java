package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.StudentsDynamicAttendance;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public interface GetLessonsOfGroupForBall {
    String getEducationYearId();
    String getGroupId();
    String getStudentId();
    String getSubjectId();
    String getSubjectName();

    @Value("#{@gradeOfStudentByTeacherRepository.getMiddleGrade(target.educationYearId,target.studentId,target.subjectId)}")
    Double getMiddleGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getAllSumGrade(target.studentId,target.educationYearId,target.subjectId)}")
    Double getAllSumGrade();

    @Value("#{@gradeForAttendanceRepository.getGradeForAttendance(target.groupId,target.educationYearId,target.subjectId)}")
    Float getGradeForAttendance();

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationYearId,target.subjectId,target.studentId)}")
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
}
