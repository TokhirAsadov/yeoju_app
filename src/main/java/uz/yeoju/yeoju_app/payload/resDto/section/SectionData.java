package uz.yeoju.yeoju_app.payload.resDto.section;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetKafedraMudiriData;

public interface SectionData {
    String getId();

    String getName();

    @Value("#{@sectionRepository.getSectionOwner(target.id)}")
    GetKafedraMudiriData getKafedraMudiri();

}
