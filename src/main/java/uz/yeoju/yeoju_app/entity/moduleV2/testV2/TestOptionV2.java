package uz.yeoju.yeoju_app.entity.moduleV2.testV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestOptionV2 extends AbsEntity {
    private String text;    // Variant matni

//    @JsonIgnore
    private Boolean correct; // Shu variant to‘g‘rimi yoki yo‘q

    @ManyToOne
    @JoinColumn(name = "question_v2_id")
    @JsonIgnore
    private TestQuestionV2 questionV2;
}
