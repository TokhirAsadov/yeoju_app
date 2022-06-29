package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String id;
    private String firstName;
    private String lastName;
    private String name;
    private String shortName;
    private String gender;
    private String email;
    private String mobile;
    private String partnerId;
}
