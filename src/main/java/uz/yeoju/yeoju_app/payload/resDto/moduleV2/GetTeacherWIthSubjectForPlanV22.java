package uz.yeoju.yeoju_app.payload.resDto.moduleV2;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetSubjectsForTeacherWithSubjectForPlan;

import java.util.Set;

public interface GetTeacherWIthSubjectForPlanV22 {
    String getId();
    String getEducationYearId();
    String getFullName();
    String getFirstName();
    String getLastName();
    String getMiddleName();

    @Value("#{@planOfSubjectV2Repository.getSubjectsForTeacherWithSubjectForPlan(target.id,target.educationYearId)}")
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjects();
}
