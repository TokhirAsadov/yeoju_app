package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;

@Entity(name = "diploma")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Diploma extends AbsEntity {
    private String diplomId;
    private String diplomSeriya;
    private String diplomRaqami;
    private String fName;
    private String mName;
    private String lName;
    private String fNameEng;
    private String lNameEng;
    private String yonalishQisqa;
    private String yonalishUzb;
    private String yonalishEng;
    private String maktab;
    private String bachelorOf;
    private String imtiyoz;
    private String imtiyozEng;

    private String login;
}
