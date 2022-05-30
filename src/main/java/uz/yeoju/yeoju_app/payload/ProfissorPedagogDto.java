package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfissorPedagogDto{

    private String id;
    private User user;
    private Set<EducationFormDto> eduFormDtos;
    private Set<EducationTypeDto> eduTypeDtos;
    private Set<EducationLanguageDto> eduLangDtos;

    private Date bornYear;
    private Timestamp enrollmentYear;// ishga kirgan yili
    private String citizenship;

//    private String academicDegree;
//    private String academicUnvon; //TODO---------------

    //TODO---------------
    // private Status status;
}
