package uz.yeoju.yeoju_app.entity.uquvbulim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionForTeacherGrading extends AbsEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private User teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lesson subject;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PPostStatus status;

    private Date deadline;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;
    @ManyToOne(fetch = FetchType.EAGER)
    private EducationYear educationYear;

    public PermissionForTeacherGrading(String id, User teacher, Lesson subject, PPostStatus status, Date deadline, Group group) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.status = status;
        this.deadline = deadline;
        this.group = group;
    }

    public PermissionForTeacherGrading(String id, User teacher, Lesson subject, PPostStatus status, Date deadline, Group group, EducationYear educationYear) {
        super(id);
        this.teacher = teacher;
        this.subject = subject;
        this.status = status;
        this.deadline = deadline;
        this.group = group;
        this.educationYear = educationYear;
    }
}
