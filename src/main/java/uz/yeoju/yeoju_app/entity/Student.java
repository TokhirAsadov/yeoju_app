package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends AbsEntity {

    @OneToOne
    private User user;

    @ManyToOne
    private Group group;

    @OneToOne
    private EducationForm educationForm;

    @OneToOne
    private EducationType educationType;

    @OneToOne
    private EducationLanguage educationLanguage;

    @OneToOne
    private Address address;

    @OneToOne
    private Address residenceAddress;

    @OneToMany
    private Set<PhoneNumber> phoneNumbers;

    private String passportSerial;
    private Date bornYear;
    private String description;
    private Date enrollmentYear;
    private String citizenship;
    private Integer level;// kursi

    // private Status status;
}
