package uz.yeoju.yeoju_app.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.chat.MessageService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/findMessagesOfChat/{id}")
    public HttpEntity<?> findMessagesByChatId(@PathVariable String id){
        System.out.println(id+" 88888888888");
        return ResponseEntity.ok(messageService.findMessagesByChatId(id));
    }

    @PostMapping("/sendMessage")
    public HttpEntity<?> sendMessage(@RequestBody MessageBuild messageDto){
        System.out.println(messageDto);
        return ResponseEntity.status(201).body(messageService.saveOrUpdate(messageDto));
    }

}
