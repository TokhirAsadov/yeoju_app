package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;

public interface KafedraResDto {
    String getId();
    String getName();
    String getRoom();
    String getPhone();

    @Value("#{@kafedraRepository.getKafedraMudiriData(target.id)}")
    GetKafedraMudiriData getKafedraMudiri();
}
