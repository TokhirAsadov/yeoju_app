package uz.yeoju.yeoju_app.entity.dekanat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcademicRecords  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
    private String qaydRaqami;
    private String berilganSana;
    private String fish;
    private String tugilganSana;
    private String akademikDaraja;
    private String fakultet;
    private String yunalish;
    private String uqishgaQabulQilinganSana;
    private String talimOluvchiningMaqomi;
    private String uqishniTamomlaganSana;
    private String fanNomi;
    private Integer semestr;
    private Integer yuklama;
    private Integer kredit;
    private Integer ball;
    private Integer baho;
    private Double gpa;

    public AcademicRecords(String qaydRaqami, String berilganSana, String fish, String tugilganSana, String akademikDaraja, String fakultet, String yunalish, String uqishgaQabulQilinganSana, String talimOluvchiningMaqomi, String uqishniTamomlaganSana, String fanNomi, Integer semestr, Integer yuklama, Integer kredit, Integer ball, Integer baho, Double gpa) {
        this.qaydRaqami = qaydRaqami;
        this.berilganSana = berilganSana;
        this.fish = fish;
        this.tugilganSana = tugilganSana;
        this.akademikDaraja = akademikDaraja;
        this.fakultet = fakultet;
        this.yunalish = yunalish;
        this.uqishgaQabulQilinganSana = uqishgaQabulQilinganSana;
        this.talimOluvchiningMaqomi = talimOluvchiningMaqomi;
        this.uqishniTamomlaganSana = uqishniTamomlaganSana;
        this.fanNomi = fanNomi;
        this.semestr = semestr;
        this.yuklama = yuklama;
        this.kredit = kredit;
        this.ball = ball;
        this.baho = baho;
        this.gpa = gpa;
    }
}
