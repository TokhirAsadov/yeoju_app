package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.TeachStatus;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @Enumerated(EnumType.STRING)
    private TeachStatus teachStatus;    // private Status status;

    private String passportSerial;
    private Timestamp bornYear;
    private String description;
    private Timestamp enrollmentYear;// uqishga kirgan yili
    private String citizenship;//fuqaroligi
    private String rektororder;
    private String lengthOfStudying;

    public Student(User user, Group group, String passportSerial, Timestamp bornYear, String description, Timestamp enrollmentYear, String citizenship) {
        this.user = user;
        this.group = group;
        this.passportSerial = passportSerial;
        this.bornYear = bornYear;
        this.description = description;
        this.enrollmentYear = enrollmentYear;
        this.citizenship = citizenship;
    }
}
