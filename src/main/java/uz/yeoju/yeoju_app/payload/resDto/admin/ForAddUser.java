package uz.yeoju.yeoju_app.payload.resDto.admin;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.address.Districts;
import uz.yeoju.yeoju_app.entity.address.Regions;
import uz.yeoju.yeoju_app.payload.resDto.role.RoleResDto;

import java.util.List;

public interface ForAddUser {
    String getId();

    @Value("#{@regionsRepository.findAll()}")
    List<Regions> getRegions();

    @Value("#{@districtsRepository.findAll()}")
    List<Districts> getDistricts();

    @Value("#{@roleRepository.getRolesNamesForSelect()}")
    List<String> getRoles();

    @Value("#{@positionRepository.getPositionsNameForSelect()}")
    List<String> getPositions();
}
