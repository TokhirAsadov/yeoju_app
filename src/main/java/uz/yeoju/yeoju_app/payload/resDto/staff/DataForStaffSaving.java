package uz.yeoju.yeoju_app.payload.resDto.staff;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;
import java.util.Set;

public interface DataForStaffSaving {

    String getId();

    @Value("#{@userRepository.getRolesForStaffSaving()}")
    List<UserForTeacherSaveItem> getRoles();

    @Value("#{@userRepository.getSectionsForStaffSaving()}")
    List<UserForTeacherSaveItem> getSections();

    @Value("#{@roleRepository.getRolesNamesForSelect()}")
    Set<String> getRoles2();

    @Value("#{@positionRepository.getPositionsNameForSelect()}")
    Set<String> getPositions();
}
