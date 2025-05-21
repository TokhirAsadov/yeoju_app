package uz.yeoju.yeoju_app.payload.resDto.moduleV2;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGroupsOfPlan;

import java.util.Set;

public interface GetExistsPlansV2 {
    String getId();
    String getTeacherId();
    String getEduType();
    String getLessonId();
    String getLessonName();
    String getEduLang();
    Integer getLevel();

    @Value("#{@planOfSubjectV2Repository.getTeacherWIthSubjectForPlanGetData(target.teacherId)}")
    GetTeacherWIthSubjectForPlanV22 getUserData();

    @Value("#{@planOfSubjectV2Repository.getGroupsOfPlan(target.id)}")
    Set<GetGroupsOfPlan> getGroups();
}
