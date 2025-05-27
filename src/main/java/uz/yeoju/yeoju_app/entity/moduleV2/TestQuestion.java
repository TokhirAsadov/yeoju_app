package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestion extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private TestType type;


    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private String questionText;

    @ElementCollection
    @CollectionTable(
            name = "TestQuestion_options",
            joinColumns = @JoinColumn(name = "TestQuestion_id")
    )
    private List<String> options; // variantlar (faqat SINGLE/MULTIPLE uchun kerak)

    @ElementCollection
    @CollectionTable(
            name = "TestQuestion_correct_answers",
            joinColumns = @JoinColumn(name = "TestQuestion_id")
    )
    @JsonIgnore
    private List<String> correctAnswers; // MULTIPLE uchun ko‘p to‘g‘ri javob, SINGLE uchun 1 dona, WRITTEN uchun bo‘sh qolishi mumkin

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserTestAnswer> answers = new ArrayList<>();

}
