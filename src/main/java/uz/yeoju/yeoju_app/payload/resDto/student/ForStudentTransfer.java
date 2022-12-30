package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

public interface ForStudentTransfer {
    String getId();
    String getFullName();
    String getGroupName();
    Integer getLevel();
    String getForm();
    String getType();
    String getLang();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhotos();
}
