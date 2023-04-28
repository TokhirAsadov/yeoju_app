package uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface RolesAndPositionsForDekanatCRUD {
    Integer getId();

    @Value("#{@roleRepository.getRolesNamesForSelect()}")
    Set<String> getRoles();

    @Value("#{@positionRepository.getPositionsNameForSelect()}")
    Set<String> getPositions();
}
