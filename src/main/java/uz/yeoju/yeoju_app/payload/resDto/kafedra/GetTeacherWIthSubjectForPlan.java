package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

import java.util.Set;

public interface GetTeacherWIthSubjectForPlan {
    public String getId();
    public String getFullName();
    public String getFirstName();
    public String getLastName();
    public String getMiddleName();

    @Value("#{@planOfSubjectRepository.getSubjectsForTeacherWithSubjectForPlan(target.id)}")
    Set<GetSubjectsForTeacherWithSubjectForPlan> getSubjects();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@positionRepository.getNameOfPosition(target.id)}")
    String getPositions();

    @Value("#{@teacherRepository.getWorkerStatus(target.id)}")
    String getStatus();
}
