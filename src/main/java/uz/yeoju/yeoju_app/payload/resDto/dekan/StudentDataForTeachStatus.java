package uz.yeoju.yeoju_app.payload.resDto.dekan;

import uz.yeoju.yeoju_app.entity.enums.TeachStatus;

public interface StudentDataForTeachStatus {


    String getId();
    String getFullName();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getLogin();
    String getRfid();
    String getPassport();
    TeachStatus getTeachStatus();
    String getRektororder();
    String getGroupName();
    String getFacultyName();



}
