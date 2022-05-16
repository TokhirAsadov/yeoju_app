package uz.yeoju.yeoju_app.entity.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PassportCopy extends AbsEntity {
    @ManyToOne
    private User user;

    @OneToOne
    private Attachment attachment;

    private boolean active;
}
