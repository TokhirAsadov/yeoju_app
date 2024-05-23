package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalGradeOfStudent extends AbsEntity {
    private Float grade;
    private Float extraGrade;
    @ManyToOne
    private User student;
    @ManyToOne
    private Vedimost vedimost;
}
