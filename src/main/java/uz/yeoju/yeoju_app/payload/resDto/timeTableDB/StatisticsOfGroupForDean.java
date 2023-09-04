package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface StatisticsOfGroupForDean {
    String getEducationId();
    String getGroupId();

    String getStudentId();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getFullName();
    String getUserId(); // login

    @Value("#{@groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentIdForDean(target.groupId,target.educationId,target.studentId)}")
    Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> getSubjects();
}
