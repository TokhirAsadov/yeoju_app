package uz.yeoju.yeoju_app.payload.resDto.user;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;

import java.util.List;

public interface UserForSectionSave {
    String getId();

    @Value("#{@userRepository.getUserForTeacherSaving()}")
    List<UserForTeacherSaveItem> getUsers();

    @Value("#{@kafedraRepository.getKafedraForTeacherSaving()}")
    List<UserForTeacherSaveItem> getKafedras();

    @Value("#{@lessonRepository.getSubjectsForTeacherSaving()}")
    List<UserForTeacherSaveItem> getSubjects();

    @Value("#{@positionRepository.getPositionsForSectionSaving(target.id)}")
    List<UserForTeacherSaveItem> getPositions();

    default WorkerStatus[] getWorkerStatus(){
        return WorkerStatus.values();
    }
}
