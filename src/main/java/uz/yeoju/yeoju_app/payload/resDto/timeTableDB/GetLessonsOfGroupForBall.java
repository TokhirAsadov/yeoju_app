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

    @Value("#{@gradeForAttendanceRepository.getValueOfGradeForAttendance(target.groupId,target.educationYearId,target.subjectId)}")
    Float getGradeForAttendance();

//    @Value("#{@gradeForAttendanceRepository.getGradeForAttendance(target.groupId,target.educationYearId,target.subjectId)}")
//    Float getGradeForAttendance();

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(target.groupId,target.educationYearId,target.subjectId,target.studentId)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();

    @Value("#{@gradeForAttendanceRepository.getAllGradesForAttendance(target.studentId,target.groupId,target.educationYearId,target.subjectId)}")
    Float getAllGradesForAttendance();

}
