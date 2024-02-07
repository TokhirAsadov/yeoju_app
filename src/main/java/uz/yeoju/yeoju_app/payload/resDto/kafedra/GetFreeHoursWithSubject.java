package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetFreeHoursWithSubject {
    String getGroupId();
    String getGroupName();
    String getSubjectId();
    String getSubject();
    String getEducationYearId();

    @Value("#{@teachersFreeHoursRepository.getFreeHours(targit.groupId,target.educationYearId,target.subjectId)}")
    Set<GetFreeHours> hours();
}
