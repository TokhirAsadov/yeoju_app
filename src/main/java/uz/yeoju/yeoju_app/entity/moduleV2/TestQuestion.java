package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestion extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "test_id")
    private CourseTest test;

    private String questionText;

    @ElementCollection
    private List<String> options; // ["O'zbekiston", "Qozog'iston", "Tojikiston", "Turkmaniston"]

    @JsonIgnore
    private String correctAnswerText; // Masalan: "O'zbekiston"
}
