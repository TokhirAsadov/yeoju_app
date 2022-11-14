package uz.yeoju.yeoju_app.payload.resDto.user;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentAddress;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentPhones;

import java.util.Date;
import java.util.List;

public interface UserField {
    String getId();
    String getFullName();
    Date getBornYear();
    String getCitizenship();
    String getNationality();
    String getPassportNum();

    String getEmail();

    @Value("#{@addressUserRepository.getAddressByUserId(target.id)}")
    StudentAddress getAddress();

    @Value("#{@addressUserRepository.getAddressCurrentByUserId(target.id)}")
    StudentAddress getAddressCurrent();

    @Value("#{@phoneNumberRepository.getPhoneNumberUserId(target.id)}")
    List<StudentPhones> getPhones();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhotos();
}
