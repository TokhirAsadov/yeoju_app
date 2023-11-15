package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

import java.util.Set;

public interface GetTeacherWIthSubjectForPlan {
    String getId();
    String getEducationYearId();
    String getFullName();
    String getFirstName();
    String getLastName();
    String getMiddleName();

    @Value("#{@planOfSubjectRepository.getSubjectsForTeacherWithSubjectForPlan(target.id,target.educationYearId)}")
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjects();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@positionRepository.getNameOfPosition(target.id)}")
    String getPositions();

    @Value("#{@teacherRepository.getWorkerStatus(target.id)}")
    String getStatus();
}
