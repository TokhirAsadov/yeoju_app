package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiplomaCreator {
    public String diplomId;
    public String diplomSeriya;
    public String diplomRaqami;
    public String fName;
    public String mName;
    public String lName;
    public String fNameEng;
    public String lNameEng;
    public String yonalishQisqa;
    public String yonalishUzb;
    public String yonalishEng;
    public String maktab;
    public String bachelorOf;
    public String imtiyoz;
    public String imtiyozEng;

    public String login;
}
