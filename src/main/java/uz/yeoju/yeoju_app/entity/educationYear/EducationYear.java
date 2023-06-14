package uz.yeoju.yeoju_app.entity.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.springframework.format.annotation.DateTimeFormat;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EducationYear extends AbsEntity {
    private String name;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date start;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date end;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Set<WeekOfEducationYear> weeks;

}
