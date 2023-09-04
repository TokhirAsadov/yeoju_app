package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

public interface GetLessonsOfGroupForBall {
    String getEducationYearId();
    String getGroupId();
    String getStudentId();
    String getSubjectId();
    String getSubjectName();

    @Value("#{@gradeOfStudentByTeacherRepository.getMiddleGrade(target.educationYearId,target.studentId,target.subjectId)}")
    Double getMiddleGrade();
}
