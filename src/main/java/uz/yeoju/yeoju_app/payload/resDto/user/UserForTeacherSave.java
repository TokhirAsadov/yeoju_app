package uz.yeoju.yeoju_app.payload.resDto.user;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetLessonsByKafedraOwnerId;

import java.util.List;

public interface UserForTeacherSave {

    String getId();

    @Value("#{@userRepository.getUserForTeacherSaving()}")
    List<UserForTeacherSaveItem> getUsers();

    @Value("#{@kafedraRepository.getKafedraForTeacherSaving()}")
    List<UserForTeacherSaveItem> getKafedras();

    @Value("#{@lessonRepository.getAllLessonByKaferaOwnerId2(target.id)}")
    List<UserForTeacherSaveItem> getSubjects();

    @Value("#{@positionRepository.getPositionsForTeacherSaving2(target.id)}")
    List<UserForTeacherSaveItem> getPositions();

    default WorkerStatus [] getWorkerStatus(){
        return WorkerStatus.values();
    }
}
