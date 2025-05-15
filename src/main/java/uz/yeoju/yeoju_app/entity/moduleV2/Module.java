package uz.yeoju.yeoju_app.entity.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module extends AbsEntity {

    private String title;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "module")
    private List<LessonModule> lessons = new ArrayList<>();

    public Module(String title, Course course) {
        this.title = title;
        this.course = course;
    }
}
