package uz.yeoju.yeoju_app.payload.payres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.TeachStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFullNameAndAscAndDescDateDto {
    private String id;
    private Boolean accountNonLocked;
    private String fullName;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String passport;
    private String userId;
    private String cardNo;
    private TeachStatus status;
    private Date dateAsc;
    private Date dateDesc;

    public StudentFullNameAndAscAndDescDateDto(String id,Boolean accountNonLocked, String fullName, String firstName, String lastName, String middleName, String email, String passport, String userId, String cardNo,TeachStatus status) {
        this.id = id;
        this.accountNonLocked = accountNonLocked;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.passport = passport;
        this.userId = userId;
        this.cardNo = cardNo;
        this.status = status;
    }
}
