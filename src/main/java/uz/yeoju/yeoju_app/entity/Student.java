package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends AbsEntity {

    @OneToOne
    private User user;

    @ManyToOne
    private Group group;

    @ManyToOne
    private EducationForm educationForm;

    @ManyToOne
    private EducationType educationType;

    @ManyToOne
    private EducationLanguage educationLanguage;


    private String passportSerial;
    private Date bornYear;
    private String description;
    private Date enrollmentYear;
    private String citizenship;
    private Integer level;// kursi

    // private Status status;
}
