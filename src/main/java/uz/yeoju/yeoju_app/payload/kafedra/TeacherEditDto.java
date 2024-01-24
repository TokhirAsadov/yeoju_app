package uz.yeoju.yeoju_app.payload.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherEditDto {

    private String id;

    private String kafedraId;
    private String fullName;
    private String login;
    private String RFID;
    private String email;
    private String passportNum;
    private String citizenship;//fuqaroligi
    private String nationality;//millati

    private String position;
    private String workStatus;
    private List<String> subjects;

    private Float rate;
    public TeacherEditDto(String kafedraId, String fullName, String login, String RFID, String email, String passportNum, String citizenship, String nationality, String position, String workStatus, List<String> subjects) {
        this.kafedraId = kafedraId;
        this.fullName = fullName;
        this.login = login;
        this.RFID = RFID;
        this.email = email;
        this.passportNum = passportNum;
        this.citizenship = citizenship;
        this.nationality = nationality;
        this.position = position;
        this.workStatus = workStatus;
        this.subjects = subjects;
    }

    //    id:'',
//    fullName:'',
//    rfid:'',
//    login:'',
//    email:'',
//    passportNum:'',
//    nationality:'',
//    citizenship:'',
//    position: '',
//    subjects:[]
}
