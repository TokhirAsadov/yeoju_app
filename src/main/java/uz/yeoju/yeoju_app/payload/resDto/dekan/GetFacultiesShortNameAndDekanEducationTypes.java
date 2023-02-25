package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetFacultiesShortNameAndDekanEducationTypes {

    String getId();

    @Value("#{@dekanatRepository.getFacultiesShortnamesWithDekanId(target.id)}")
    Set<String> getShortNames();

    @Value("#{@dekanRepository.getDekanEducationTypes(target.id)}")
    Set<String> getEducationTypes();

}
