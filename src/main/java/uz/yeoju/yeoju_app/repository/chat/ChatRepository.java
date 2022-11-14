package uz.yeoju.yeoju_app.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.chat.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,String> {

    @Query(value = "select f1.* from\n" +
            "(select ch.* from Chat ch join Chat_users ch_u\n" +
            "on ch.id = ch_u.Chat_id\n" +
            "join users u on ch_u.members_id = u.id\n" +
            "where ch_u.members_id=:first) as f1\n" +
            "    join\n" +
            "(select ch.* from Chat ch join Chat_users ch_u\n" +
            "on ch.id = ch_u.Chat_id\n" +
            "join users u on ch_u.members_id = u.id\n" +
            "where ch_u.members_id=:second) as f2 on\n" +
            "    f1.id=f2.id",nativeQuery = true)
    Chat findChat(@Param(value = "first") String first, @Param(value = "second") String second);

//    @Query(value = "select ch.* from Chat ch join Chat_users ch_u on ch.id = ch_u.Chat_id where  ch_u.members_id=:id",nativeQuery = true)
//    List<Chat> findUserChats(@Param("id") String id);

    @Query(value = "select ch.* from Chat ch join Chat_users ch_u\n" +
            "  on ch.id = ch_u.Chat_id\n" +
            "join users u on ch_u.members_id = u.id\n" +
            "where ch_u.members_id=:id",nativeQuery = true)
    List<Chat> findUserChats(@Param("id") String id);

    @Query(value = "select * from Chat ch where ch.id=:id",nativeQuery = true)
    Chat forRes(@Param("id") String id);
}
