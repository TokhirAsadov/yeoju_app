package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationOuter2 extends AbsEntity {// sirtqi talimda uqiyatgan talabalar uchun xabarnoma talabaning ish joyi uchun

    private Long queue;
    @ManyToOne
    private Dekanat dekanat;

    @ManyToOne
    private EducationYear educationYear;

    private Integer course;
    private Date fromDate;
    private Date toDate;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<Faculty> faculties;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<Group> groups;
    private String dynamicSection;

    public NotificationOuter2(String id, Dekanat dekanat, EducationYear educationYear, Integer course, Date fromDate, Date toDate, Set<Faculty> faculties, Set<Group> groups) {
        super(id);
        this.dekanat = dekanat;
        this.educationYear = educationYear;
        this.course = course;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.faculties = faculties;
        this.groups = groups;
    }
}
