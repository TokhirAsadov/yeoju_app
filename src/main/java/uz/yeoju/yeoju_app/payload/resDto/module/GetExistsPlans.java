package uz.yeoju.yeoju_app.payload.resDto.module;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetExistsPlans {
    String getId();
    String getEduType();
    String getLessonId();
    String getLessonName();
    String getEduLang();
    Integer getLevel();

    @Value("#{@planOfSubjectRepository.getGroupsOfPlan(target.id)}")
    Set<GetGroupsOfPlan> getGroups();
}
