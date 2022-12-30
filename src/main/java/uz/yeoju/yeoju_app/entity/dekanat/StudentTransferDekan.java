package uz.yeoju.yeoju_app.entity.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentTransferDekan extends AbsEntity {

    private String description;
    @ManyToOne
    private User student;

    @ManyToOne
    private User fromDekan;

    @ManyToOne
    private User toDekan;

    @Enumerated(EnumType.STRING)
    private StudentTransferStatus status;

    @ManyToOne
    private Group fromGroup;

    @ManyToOne
    private Group toGroup;

    public StudentTransferDekan(String id, String description, User student, User fromDekan, User toDekan, StudentTransferStatus status, Group fromGroup, Group toGroup) {
        super(id);
        this.description = description;
        this.student = student;
        this.fromDekan = fromDekan;
        this.toDekan = toDekan;
        this.status = status;
        this.fromGroup = fromGroup;
        this.toGroup = toGroup;
    }
}
