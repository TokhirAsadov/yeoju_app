package uz.yeoju.yeoju_app.payload.resDto.module.student;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetFreeHours;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetFreeHoursWithSubject;
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
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Double getTodayGrade();

    @Value("#{@gradeOfStudentByTeacherRepository.getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonId(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Set<GetGradesOfStudent> getGrades();
}
