package uz.yeoju.yeoju_app.entity.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeachersFreeHours extends AbsEntity {

    @ManyToOne
    private EducationYear educationYear;
    private String day;
    private String schedule;

    public TeachersFreeHours(String id, EducationYear educationYear, String day, String schedule) {
        super(id);
        this.educationYear = educationYear;
        this.day = day;
        this.schedule = schedule;
    }
}
