package uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

import java.util.List;

public interface KafedraMudiriForRektorTeacherPage {
    String getId();
    String getLogin();
    String getPassport();
    String getFullName();
    String getEmail();

    @Value("#{@phoneNumberRepository.getKafedraMudiriPhonesForRektorTeacherPage(target.id)}")
    List<KafedraMudiriPhonesForRektorTeacherPage> getPhones();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhotos();
}
