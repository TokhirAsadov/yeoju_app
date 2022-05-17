package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class YearOfAppointment extends AbsEntity {

    @ManyToOne
    private ProfissorPedagog profissorPedagog;

    @ManyToOne
    private Role role;

    private Timestamp startYear;
}
