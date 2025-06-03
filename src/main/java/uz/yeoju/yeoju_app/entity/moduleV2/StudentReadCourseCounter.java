package uz.yeoju.yeoju_app.entity.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentReadCourseCounter extends AbsEntity {
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private Integer count=0;
}
