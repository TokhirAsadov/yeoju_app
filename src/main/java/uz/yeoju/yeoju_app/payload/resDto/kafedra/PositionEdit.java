package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface PositionEdit {

    String getId();
    String getTeacherId();

    @Value("#{@positionRepository.getUserPositionForEditUserPosition(target.teacherId)}")
    UserForTeacherSaveItem getUserPosition();

    @Value("#{@positionRepository.getPositionsForTeacherSaving2(target.id)}")
    List<UserForTeacherSaveItem> getPositions();

    @Value("#{@lessonRepository.getLessonNames(target.id)}")
    List<String> getSubjects();
}
