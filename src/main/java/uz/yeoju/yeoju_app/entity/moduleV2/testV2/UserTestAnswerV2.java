package uz.yeoju.yeoju_app.entity.moduleV2.testV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestAnswerV2 extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_v2_id")
    @JsonIgnore
    private TestQuestionV2 questionV2;

    @ManyToMany
    @JoinTable(
            name = "UserTestAnswerSelectedOptionsV2",
            joinColumns = @JoinColumn(name = "user_test_answer_v2_id"),
            inverseJoinColumns = @JoinColumn(name = "test_option_v2_id")
    )
    private List<TestOptionV2> selectedOptionsV2;

    private String writtenAnswer;

    private boolean isCorrect;

    @Column(nullable = false)
    private boolean shouldBeDeleted = false;
}

