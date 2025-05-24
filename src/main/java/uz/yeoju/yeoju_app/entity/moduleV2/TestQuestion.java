package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
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
    @CollectionTable(
            name = "TestQuestion_options",
            joinColumns = @JoinColumn(name = "TestQuestion_id")
    )
    private List<String> options; // ["O'zbekiston", "Qozog'iston", "Tojikiston", "Turkmaniston"]

    @JsonIgnore
    private String correctAnswerText; // Masalan: "O'zbekiston"
}
