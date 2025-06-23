package uz.yeoju.yeoju_app.entity.moduleV2;

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
public class UserTestAnswer extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private TestQuestion question;

    @ManyToMany
    @JoinTable(
            name = "user_test_answer_selected_options",
            joinColumns = @JoinColumn(name = "user_test_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "test_option_id")
    )
    private List<TestOption> selectedOptions;

    private String writtenAnswer;

    private boolean isCorrect;

    @Column(nullable = false)
    private boolean shouldBeDeleted = false;

    private Integer score = 0;
}

