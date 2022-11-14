package uz.yeoju.yeoju_app.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.chat.ChatService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/createChat")
    public HttpEntity<?> create(@CurrentUser User user, @RequestParam String receiverId){
        return ResponseEntity.ok(chatService.save(user,receiverId));
    }

    @GetMapping("/find/{firstId}/{secondId}")
    public HttpEntity<?> findChat(@PathVariable String firstId, @PathVariable String secondId){
        return ResponseEntity.ok(chatService.findChat(firstId,secondId));
    }

    @GetMapping("/findUserChat/{id}")
    public HttpEntity<?> findChats(@PathVariable String id){
        return ResponseEntity.ok(chatService.findUserChats(id));
    }

    @GetMapping("/findChat/{id}")
    public HttpEntity<?> findChatById(@PathVariable String id){
        return ResponseEntity.ok(chatService.findById(id));
    }
}
