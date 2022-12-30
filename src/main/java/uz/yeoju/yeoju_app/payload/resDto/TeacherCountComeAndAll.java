package uz.yeoju.yeoju_app.payload.resDto;

import org.springframework.beans.factory.annotation.Value;

public interface TeacherCountComeAndAll {
    String getId();
    Integer getComeCount();
    Integer getAllCount();

    @Value("#{@kafedraRepository.getNameOfKafedraById(target.id)}")
    String getKafedraName();
}
