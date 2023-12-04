package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

public interface StudentData {
    String getId();
    String getLogin();
    String getFullName();
    Date getBornYear();
    String getCitizenship();
    String getNationality();
    String getPassportNum();
    String getEmail();
    String getRFID();
    String getTeachStatus();
    String getLengthOfStudying();
    String getRektororder();



    @Value("#{@addressUserRepository.getAddressByUserId(target.id)}")
    StudentAddress getAddress();

    @Value("#{@phoneNumberRepository.getPhoneNumberUserId(target.id)}")
    List<StudentPhones> getPhones();

    @Value("#{@groupRepository.getGroupFieldByUserId(target.id)}")
    StudentGroupField getGroupData();

    @Value("#{@studentResultRepository.getStudentResultsByUserId(target.id)}")
    List<StudentResults> getResults();

    @Value("#{@groupRepository.getDeanId(target.id)}")
    String getDeanId();
}
