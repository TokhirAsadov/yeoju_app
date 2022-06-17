package uz.yeoju.yeoju_app.payload.payres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFullNameAndAscAndDescDateDto {
    private Integer id;
    private String fullName;
    private String cardNo;
    private Date dateAsc;
    private Date dateDesc;

    public StudentFullNameAndAscAndDescDateDto(Integer id, String fullName, String cardNo) {
        this.id = id;
        this.fullName = fullName;
        this.cardNo = cardNo;
    }
}
