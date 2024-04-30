package uz.yeoju.yeoju_app.payload.resDto.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public interface GetGradesOfStudent {
    String getId();
    String getFailGradeId();
    String getThemeId();
    String getTheme();
    Double getMaxGrade();
    Float getGrade();
    Timestamp getTime();
    Timestamp getCreatedAt();
    @JsonIgnore
    String getDescription();


    default Set<String> getRetakes(){
        return new HashSet<>();
    };
}
