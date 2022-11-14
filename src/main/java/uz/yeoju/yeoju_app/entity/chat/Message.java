package uz.yeoju.yeoju_app.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message extends AbsEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Chat Chat;

    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;

    private String text;

    public Message(String id, Chat Chat, User sender, String text) {
        super(id);
        this.Chat = Chat;
        this.sender = sender;
        this.text = text;
    }
}
