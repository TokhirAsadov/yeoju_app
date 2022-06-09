package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class YearOfAppointment extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private ProfissorPedagog profissorPedagog;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    private Timestamp startYear;
}
