package uz.yeoju.yeoju_app.controller.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBuild {
    private String id;
    private String chatId;
    private String senderId;
    private String text;
    private Timestamp createdAt;
}
