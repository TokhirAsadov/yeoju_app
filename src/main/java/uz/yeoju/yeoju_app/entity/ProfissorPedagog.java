package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProfissorPedagog extends AbsEntity {

    @OneToOne
    private User user;

    @ManyToMany
    private Set<EducationForm> educationForms;

    @ManyToMany
    private Set<EducationType> educationTypes;

    @ManyToMany
    private Set<EducationLanguage> educationLanguages;

    private Timestamp bornYear;
    private Timestamp enrollmentYear;// ishga kirgan yili
    private String citizenship;

//    private String academicDegree;
//    private String academicUnvon; //TODO---------------

    //TODO---------------
    // private Status status;
}
