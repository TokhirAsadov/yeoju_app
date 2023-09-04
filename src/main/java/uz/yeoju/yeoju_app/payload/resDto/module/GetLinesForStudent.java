package uz.yeoju.yeoju_app.payload.resDto.module;


import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface GetLinesForStudent {
    String getPlanId();

    @Value("#{@lineOfPlanRepository.getLines(target.planId)}")
    List<GetListOfPlan> getLines();
}
