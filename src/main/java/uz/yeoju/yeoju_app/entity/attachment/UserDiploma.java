package uz.yeoju.yeoju_app.entity.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserDiploma extends AbsEntity {
    @ManyToOne
    private User user;

    @ManyToMany
    private Set<Attachment> attachmentList;

    private boolean active;
}
