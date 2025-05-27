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

//    private String selectedOption; // foydalanuvchi tanlagan variant: "A", "B", "C", "D"

    @ElementCollection
    @CollectionTable(
            name = "UserTestAnswer_selected_options",
            joinColumns = @JoinColumn(name = "UserTestAnswer_id")
    )
    private List<String> selectedOptions; // MULTIPLE uchun ["A", "C"]
    private String writtenAnswer; // WRITTEN uchun


    private boolean isCorrect;
}

