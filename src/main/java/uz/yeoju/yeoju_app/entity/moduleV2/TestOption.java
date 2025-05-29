package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestOption extends AbsEntity {
    private String text;    // Variant matni

    private Integer score;  // To‘g‘ri bo‘lsa necha ball

    @JsonIgnore
    private Boolean correct; // Shu variant to‘g‘rimi yoki yo‘q

    @ManyToOne
    @JoinColumn(name = "question_id")
    private TestQuestion question;
}
