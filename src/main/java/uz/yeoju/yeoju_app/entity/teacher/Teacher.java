package uz.yeoju.yeoju_app.entity.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends AbsEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Enumerated(EnumType.STRING)
    private WorkerStatus workerStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    private Kafedra kafedra;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Lesson> subjects;

    private Float rate;

}
