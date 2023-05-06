package uz.yeoju.yeoju_app.payload.resDto.admin;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.Gander;

import java.util.Set;

public interface GetUserForUpdate {
    String getId();
    String getFullName();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getLogin();
    String getRFID();
    String getPassport();
    Long getGanderId();

    @Value("#{@roleRepository.getUserRolesName(target.id)}")
    Set<String> getRoles();

    @Value("#{@positionRepository.getUserPositionsName(target.id)}")
    Set<String> getPositions();

    @Value("#{@addressUserRepository.getUserAddressForUpdateByUserId(target.id)}")
    AddressForUserUpdate getAddress();
}
