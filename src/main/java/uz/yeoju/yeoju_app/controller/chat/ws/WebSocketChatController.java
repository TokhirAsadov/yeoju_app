package uz.yeoju.yeoju_app.controller.chat.ws;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import uz.yeoju.yeoju_app.entity.ws.MsMessageDekan;
import uz.yeoju.yeoju_app.repository.StudentTransferDekanRepository;

@Controller
public class WebSocketChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private StudentTransferDekanRepository studentTransferDekanRepository;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public MsMessageDekan receivePublicMessage(@Payload MsMessageDekan msMessageDekan){
        return msMessageDekan;
    }

    @MessageMapping("/private-message")
    public MsMessageDekan receivePrivateMessage(@Payload MsMessageDekan msMessageDekan){



//        if (msMessageDekan.getTransfer()!=null) {
//            studentTransferDekanRepository.saveAndFlush(msMessageDekan.getTransfer());
//        }
//
//
//        MsMessageDekan msMessage = new MsMessageDekan();


        simpMessagingTemplate.convertAndSendToUser(msMessageDekan.getReceiverId(),"/private",msMessageDekan); // /user/userId/private


        return msMessageDekan;
    }

}
