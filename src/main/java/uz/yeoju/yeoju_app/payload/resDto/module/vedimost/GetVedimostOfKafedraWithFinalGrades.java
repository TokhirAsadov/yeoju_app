package uz.yeoju.yeoju_app.payload.resDto.module.vedimost;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Set;

public interface GetVedimostOfKafedraWithFinalGrades {
    String getId();
    Integer getLevel();
    Timestamp getDeadline();
    Timestamp getTimeClose();
    String getCondition();
    String getTeacher();
    String getLesson();
    String getGroupName();
    @Value("#{@vedimostRepository.getAllFinalGradesOfVedimost(target.id)}")
    Set<GetAllFinalGradesOfVedimost> getGrades();
}