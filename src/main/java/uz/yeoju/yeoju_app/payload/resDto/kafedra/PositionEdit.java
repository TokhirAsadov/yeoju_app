package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface PositionEdit {

    String getId();

    @Value("#{@positionRepository.getUserPositionForEditUserPosition(target.id)}")
    UserForTeacherSaveItem getUserPosition();

    @Value("#{@positionRepository.getPositionsForTeacherSaving()}")
    List<UserForTeacherSaveItem> getPositions();
}
