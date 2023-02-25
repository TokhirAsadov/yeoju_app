package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentChangeDto {
    private String id;
    private String fullName;
    private String login;
    private String RFID;
    private String email;
    private String passportNum;
    private String citizenship;//fuqaroligi
    private String nationality;//millati

    private String group;//millati
    private Integer level;//millati

}
