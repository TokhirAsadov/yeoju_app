package uz.yeoju.yeoju_app.payload.resDto.student;

import uz.yeoju.yeoju_app.entity.enums.TeachStatus;

public interface StudentWithRFID {

    String getId();
    Boolean getAccountNonLocked();
    String getFullName();
    String getRFID();
    String getLogin();
    String getPassport();
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getEmail();
    TeachStatus getStatus();

}
