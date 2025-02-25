package uz.yeoju.yeoju_app.payload.dekanat;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface DiplomaRestDto {
    String getUserId();
    String getFullName();
    String getLogin();
    String getDiplomId();
    @JsonProperty("ID")
    String getID();
    String getDiplomSeriya();
    String getDiplomRaqami();
    @JsonProperty("fName")
    String getFName();
    @JsonProperty("mName")
    String getMName();
    @JsonProperty("lName")
    String getLName();
    @JsonProperty("fNameEng")
    String getFNameEng();
    @JsonProperty("lNameEng")
    String getLNameEng();
    String getYonalishQisqa();
    String getYonalishUzb();
    String getYonalishEng();
    String getMaktab();
    String getBachelorOf();
    String getImtiyoz();
    String getImtiyozEng();
}
