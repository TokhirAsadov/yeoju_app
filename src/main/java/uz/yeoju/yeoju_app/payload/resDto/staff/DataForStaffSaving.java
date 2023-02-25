package uz.yeoju.yeoju_app.payload.resDto.staff;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface DataForStaffSaving {

    String getId();

    @Value("#{@userRepository.getRolesForStaffSaving()}")
    List<UserForTeacherSaveItem> getRoles();

    @Value("#{@userRepository.getSectionsForStaffSaving()}")
    List<UserForTeacherSaveItem> getSections();
}
