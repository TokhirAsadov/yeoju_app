package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Referral extends AbsEntity {// sirtqi talimda uqiyatgan talabalar uchun yo`llanma talabaning ish joyi uchun

    private Long queue;
    @ManyToOne
    private Dekanat dekanat;

    private Long numberOfDecision;
    private Date decisionDate;

    private Integer course;
    private Date fromDate;
    private Date toDate;

    @ManyToMany
    private Set<Faculty> faculties;

    @ManyToMany
    private Set<Group> groups;

}
