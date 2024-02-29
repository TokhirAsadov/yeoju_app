package uz.yeoju.yeoju_app.payload.resDto.module.student;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetFreeHours;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGradesOfStudent;

import java.util.Set;

public interface GetStudentGradesByLessons {
    String getStudentId();
    String getEducationYearId();
    String getGroupId();
    String getLessonId();
    String getLessonName();

    String getTeacherId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getFullName();

    @Value("#{@teachersFreeHoursRepository.getFreeHours(target.groupId,target.educationYearId,target.lessonId)}")
    Set<GetFreeHours> getHours();

    @Value("#{@gradeOfStudentByTeacherRepository.getMaxStep(target.educationYearId,target.lessonId,target.groupId)}")
    Long getMaxStep();
    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Double getSumGrade2();
    @Value("#{@gradeOfStudentByTeacherRepository.getAllSumGrade(target.studentId,target.educationYearId,target.lessonId)}")
    Double getAllSumGrade();

    @Value("#{@gradeForAttendanceRepository.getAllGradesForAttendance(target.studentId,target.groupId,target.educationYearId,target.lessonId)}")
    Float getAllGradesForAttendance();
    default Double getSumGrade(){
        if(this.getAllGradesForAttendance() == null) {
            return this.getAllSumGrade();
        }
        else if (this.getAllSumGrade()==null){
            return Double.valueOf(this.getAllGradesForAttendance());
        }
        else {
            return this.getAllSumGrade()+this.getAllGradesForAttendance();
        }
    };

    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Double getTodayGrade();

    @Value("#{@gradeOfStudentByTeacherRepository.getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonId(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Set<GetGradesOfStudent> getGrades();
}
