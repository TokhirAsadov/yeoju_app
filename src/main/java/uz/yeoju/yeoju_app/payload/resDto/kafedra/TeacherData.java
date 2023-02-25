package uz.yeoju.yeoju_app.payload.resDto.kafedra;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentAddress;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentPhones;

import java.util.Date;
import java.util.List;

public interface TeacherData {

    String getId();
    String getLogin();
    String getFullName();
    Date getBornYear();
    String getEmail();
    String getCitizenship();
    String getNationality();
    String getPassportNum();
    String getPassport();
    String getWorkerStatus();

    @Value("#{@positionRepository.getNamesOfPosition(target.id)}")
    List<String> getPositions();


    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@addressUserRepository.getAddressByUserId(target.id)}")
    StudentAddress getAddress();

    @Value("#{@phoneNumberRepository.getPhoneNumberUserId(target.id)}")
    List<StudentPhones> getPhones();

    @Value("#{@teacherRepository.getFacultyNameByUserId(target.id)}")
    String getKafedraName();


}
