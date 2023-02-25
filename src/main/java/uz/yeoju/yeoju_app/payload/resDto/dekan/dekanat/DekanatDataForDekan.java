package uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface DekanatDataForDekan {

    String getId();
    String getName();

    @Value("#{@dekanatRepository.getFacultiesOfDekanatByDekanatId(target.id)}")
    List<FacultyOfDekanat> getFaculties();
}
