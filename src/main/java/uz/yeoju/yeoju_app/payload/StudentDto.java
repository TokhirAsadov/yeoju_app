package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto{

    private String id;
    private UserDto userDto;
    private GroupDto groupDto;

//    @ManyToOne
//    private TeachStatus teachStatus;    // private Status status;

    private String passportSerial;
    private Timestamp bornYear;
    private String description;
    private Timestamp enrollmentYear;// uqishga kirgan yili
    private String citizenship;//fuqaroligi

}
