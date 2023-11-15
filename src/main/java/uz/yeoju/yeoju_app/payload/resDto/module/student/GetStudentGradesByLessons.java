package uz.yeoju.yeoju_app.payload.resDto.module.student;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("#{@gradeOfStudentByTeacherRepository.getMaxStep(target.educationYearId,target.lessonId,target.groupId)}")
    Long getMaxStep();
    @Value("#{@gradeOfStudentByTeacherRepository.getSumGrade(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Double getSumGrade();
    @Value("#{@gradeOfStudentByTeacherRepository.getTodayGrade(target.teacherId,target.studentId,target.educationYearId,target.lessonId)}")
    Double getTodayGrade();
}
