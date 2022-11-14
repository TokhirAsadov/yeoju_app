package uz.yeoju.yeoju_app.entity.chat;

import lombok.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Chat extends AbsEntity {

//    private String name = UUID.randomUUID().toString();
//
//    @ManyToOne
//    private User sender;
//
//    @ManyToOne
//    private User receiver;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> members;

}
