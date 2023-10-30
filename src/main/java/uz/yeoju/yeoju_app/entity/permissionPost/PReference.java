package uz.yeoju.yeoju_app.entity.permissionPost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PReference extends AbsEntity {
    private Long queue;// dekanat tasdiqlaganlarining tartibi
    private Long numeration;// jami jo`natilgan arizalarning raqami
    @ManyToOne
    private User student;
    @ManyToOne
    private User dean;
    @ManyToOne
    private EducationYear educationYear;
    @Enumerated(EnumType.STRING)
    private PPostStatus status;
    @Enumerated(EnumType.STRING)
    private TypeOfReference type;
    private String description;
}
