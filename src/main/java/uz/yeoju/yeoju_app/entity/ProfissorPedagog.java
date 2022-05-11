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
public class ProfissorPedagog extends AbsEntity {

    @OneToOne
    private User user;

    @OneToOne
    private EducationForm educationForm;

    @OneToOne
    private EducationType educationType;

    @OneToOne
    private EducationLanguage educationLanguage;

    @OneToOne
    private Address address;

    @OneToMany
    private Set<PhoneNumber> phoneNumbers;

    @OneToOne
    private Attachment photo;

    @OneToOne
    private Attachment passportCopy;

    @OneToOne
    private Attachment obyektivka;

    private Date bornYear;
    private String citizenship;


    private String academicDegree;
    private String academicUnvon; //TODO---------------

    //TODO---------------
    // private Status status;
}
