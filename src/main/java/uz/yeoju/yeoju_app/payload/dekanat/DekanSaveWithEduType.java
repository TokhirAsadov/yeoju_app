package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DekanSaveWithEduType {
    private String userId;
    private String dekanatId;
    private Set<String> edu;
}
