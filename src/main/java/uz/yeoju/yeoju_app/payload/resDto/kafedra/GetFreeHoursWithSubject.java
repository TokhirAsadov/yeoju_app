package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetFreeHoursWithSubject {
    @JsonIgnore
    String getGroupId();
    String getGroupName();
    @JsonIgnore
    String getSubjectId();
    String getSubject();
    @JsonIgnore
    String getEducationYearId();

    @Value("#{@teachersFreeHoursRepository.getFreeHours(target.groupId,target.educationYearId,target.subjectId)}")
    Set<GetFreeHours> getHours();
}
