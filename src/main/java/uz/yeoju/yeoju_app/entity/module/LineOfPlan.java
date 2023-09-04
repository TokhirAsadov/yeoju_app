package uz.yeoju.yeoju_app.entity.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LineOfPlan extends AbsEntity {
    private Integer queue;
    @ManyToOne
    private PlanOfSubject planOfSubject;
    @ManyToOne
    private ThemeOfSubject theme;
}
