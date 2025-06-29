package uz.yeoju.yeoju_app.entity.moduleV2.testV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionV2 extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private TestType type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String questionText;

    @OneToMany(mappedBy = "questionV2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestOptionV2> options = new ArrayList<>();

    @OneToMany(mappedBy = "questionV2", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserTestAnswerV2> answers = new ArrayList<>();

}
