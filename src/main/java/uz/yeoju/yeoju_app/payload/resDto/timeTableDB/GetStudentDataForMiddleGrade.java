package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetStudentDataForMiddleGrade {
    String getId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getFullName();
    String getEducationYearId();
    String getGroupId();

    @Value("#{@groupConnectSubjectRepository.getLessonsOfGroupForBall(target.educationYearId,target.groupId,target.id)}")
    Set<GetLessonsOfGroupForBall> getSubjects();

}
