package uz.yeoju.yeoju_app.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.chat.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,String> {

    @Query(value = "select * from Message m where Chat_id=:id order by createdAt asc",nativeQuery = true)
    List<Message> getMessagesByChatId(@Param("id") String id);
}
