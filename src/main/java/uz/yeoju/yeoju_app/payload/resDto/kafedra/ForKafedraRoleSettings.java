package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;
import java.util.Set;

public interface ForKafedraRoleSettings {

    String getId();

    @Value("#{@kafedraRepository.getKafedraForSaved()}")
    List<UserForTeacherSaveItem> getUsers();

    @Value("#{@kafedraRepository.getKafedraForKafedraMudiriSaved()}")
    List<UserForTeacherSaveItem> getDekanats();

    @Value("#{@roleRepository.getRolesNamesForSelect()}")
    Set<String> getRoles();

    @Value("#{@positionRepository.getPositionsNameForSelect()}")
    Set<String> getPositions();
}
