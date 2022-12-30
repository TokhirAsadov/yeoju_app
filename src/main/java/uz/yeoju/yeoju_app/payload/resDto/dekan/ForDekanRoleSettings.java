package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface ForDekanRoleSettings {

    String getId();

    @Value("#{@dekanatRepository.getDekansForSaved()}")
    List<UserForTeacherSaveItem> getUsers();

    @Value("#{@dekanatRepository.getDekanatsForDekanSaved()}")
    List<UserForTeacherSaveItem> getDekanats();
}
