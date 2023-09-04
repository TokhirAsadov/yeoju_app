package uz.yeoju.yeoju_app.payload.resDto.module.downloadCounter;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetPlanIdAndStudentId {
    String getPlanId();
    String getStudentId();

    @Value("#{@downloadCounterRepository.getLineAndThemeData(target.planId,target.studentId)}")
    Set<GetLineAndThemeData> getLines();
}
