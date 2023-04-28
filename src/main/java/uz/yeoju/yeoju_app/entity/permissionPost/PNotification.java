package uz.yeoju.yeoju_app.entity.permissionPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PNotification extends AbsEntity {

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userTo;


    @ManyToOne(fetch = FetchType.EAGER)
    private User userFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PPostStatus type;

    @Column(name = "delivered")
    private boolean delivered;

    @Column(name = "read")
    private boolean read;

}
